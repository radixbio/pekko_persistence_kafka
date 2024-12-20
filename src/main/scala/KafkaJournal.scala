package com.radix.shared.persistence

import org.apache.pekko.NotUsed
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.{ActorLogging, ActorRef}
import org.apache.pekko.cluster.ddata.{DistributedData, GCounter, GCounterKey, SelfUniqueAddress}
import org.apache.pekko.cluster.ddata.Replicator.{Get, GetSuccess, NotFound, ReadLocal, Update, UpdateFailure, UpdateSuccess, WriteLocal}
import org.apache.pekko.kafka.Metadata.{EndOffsets, GetEndOffsets}

import scala.collection.immutable
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success, Try}
import com.typesafe.config.Config
import org.apache.pekko.kafka.{CommitterSettings, ConsumerSettings, KafkaConsumerActor, ProducerMessage, ProducerSettings, Subscriptions}
import org.apache.pekko.kafka.scaladsl.{Committer, Consumer, Producer}
import org.apache.pekko.kafka.scaladsl.Consumer.DrainingControl
import org.apache.pekko.persistence.{AtomicWrite, PersistentRepr}
import org.apache.pekko.persistence.journal.{AsyncRecovery, AsyncWriteJournal}
import org.apache.pekko.serialization.{Serialization, SerializationExtension}
import org.apache.pekko.stream.Materializer
import org.apache.pekko.stream.scaladsl.{Flow, Keep, Sink, Source}
import io.confluent.kafka.serializers.{KafkaAvroDeserializer, KafkaAvroSerializer}
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{Deserializer, Serializer, StringDeserializer, StringSerializer}
import org.apache.pekko.pattern.ask
import org.apache.pekko.util.Timeout
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.{ConsumerConfig, RetriableCommitFailedException}
import org.apache.kafka.common.TopicPartition

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object KafkaJournal {
  val journalPostfix = "-journal"
}

class KafkaJournal(cfg: Config) extends AsyncWriteJournal with AsyncRecovery with ActorLogging {

  override def receivePluginInternal: Receive = {
    case success: UpdateSuccess[_] => log.debug(s"update success for ${success.key}")
    case fail: UpdateFailure[_]    => log.error(s"update failed for ${fail.key}")
    case els                       => super.receivePluginInternal(els)
  }

  private val localConfig = new KafkaConfig(cfg)

  lazy val serializationExtension: Serialization = SerializationExtension.get(context.system)
  implicit val mat: Materializer = Materializer(context.system)
  implicit val timeout: Timeout = Timeout(5.seconds)

  private val producerSettings: ProducerSettings[String, Object] = {
    val valueSerializer = new KafkaAvroSerializer().asInstanceOf[Serializer[Object]]
    valueSerializer.configure(localConfig.avroConfig, false)
    ProducerSettings(localConfig.producerConfig, new StringSerializer, valueSerializer)
      .withBootstrapServers(localConfig.bootstrapServers)
  }
  private val kafkaProducer = producerSettings.createKafkaProducer()

  private val consumerSettings: ConsumerSettings[String, Object] = {
    val valueDeserializer = new KafkaAvroDeserializer().asInstanceOf[Deserializer[Object]]
    valueDeserializer.configure(localConfig.avroConfig, false)
    ConsumerSettings(localConfig.consumerConfig, new StringDeserializer, valueDeserializer)
      .withGroupId("radix-consumers")
      .withProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
      .withProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000")
      .withBootstrapServers(localConfig.bootstrapServers)
  }
  private val metadataConsumer: ActorRef = context.system.actorOf(KafkaConsumerActor.props(consumerSettings))

  private val committerSettings: CommitterSettings = CommitterSettings(context.system)

  val replicator: ActorRef = DistributedData(context.system).replicator
  val HighestSeqNrKey: String => GCounterKey = { pId =>
    GCounterKey("radix-journal-hsn-" ++ pId)
  }
  implicit val node: SelfUniqueAddress = DistributedData(context.system).selfUniqueAddress

  private def getEndOffset(partition: TopicPartition): Future[Long] =
    for {
      response <- metadataConsumer ? GetEndOffsets(Set(partition))
      endOffsets = response match {
        case EndOffsets(Success(topicOffsetMap)) => topicOffsetMap.values.head
      }
    } yield endOffsets

  override def asyncWriteMessages(messages: immutable.Seq[AtomicWrite]): Future[immutable.Seq[Try[Unit]]] = {
    val msgSource: Source[AtomicWrite, NotUsed] = Source.fromIterator(() => messages.iterator)

    msgSource
      .groupBy(localConfig.streamParallelism, _.persistenceId)
      .mapAsync(localConfig.streamParallelism)(atomicWrite =>
        Future {
          val pekkoRecords = atomicWrite.payload.toList

          val kafkaObjects: List[Either[Throwable, (String, KafkaJournalKey, Object)]] = pekkoRecords
            .map(record => {
              val topic = record.persistenceId + KafkaJournal.journalPostfix

              val serializedObjectT = AnyAvroToSerializedObject(serializationExtension, record.payload)
              serializedObjectT match {
                case Failure(err)           => Left(err)
                case Success((serIdO, obj)) =>
                  /* If the input object doesn't have a manifest (because it's a plain
                   * String, Int, etc), Pekko requires that the manifest be empty. It is
                   * assumed that if an object does not have a serializer ID, then it should
                   * not have a manifest.
                   */
                  val key = serIdO match {
                    case None        => KafkaJournalKey(record, 0).copy(manifest = "")
                    case Some(serId) => KafkaJournalKey(record, serId)
                  }
                  Right(topic, key, obj)
              }
            })
          // a plain scala implementation of .sequenceU from scalaz
          // fail the whole set if serialization fails
          val kafkaObjectsE: Either[Throwable, List[(String, KafkaJournalKey, Object)]] =
            kafkaObjects.find(_.isLeft) match {
              case Some(left) =>
                Left(
                  left.swap.getOrElse(
                    throw new IllegalStateException("this error should never be reached")
                  )
                )
              case None =>
                Right(
                  kafkaObjects.map(
                    _.getOrElse(
                      throw new IllegalStateException("this error should never be reached")
                    )
                  )
                )
            }

          val kafkaObjectToProducerRecord: ((String, KafkaJournalKey, Object)) => ProducerRecord[String, Object] = {
            case (topic, key, obj) => {
              // uncomment to autostore timestamp from the messages. Might be useful for prismuservice in the future?
//                      val genRecord = obj.asInstanceOf[GenericRecord]
//                      val schema = genRecord.getSchema
//                      val hasTimestamp = schema.getFields.contains("timestamp") //Assume this is a some or none
//                      if (hasTimestamp){
//                        val timeStamp = genRecord.get("timestamp") match {
//                          case None => ??? //TODO getTimeStamp
//                          case a => a
//                        }
//                        genRecord.put("timestamp", timeStamp)
//                        new ProducerRecord[String, Object](topic, 0, key.toString(), genRecord.asInstanceOf[Object])
//                      }
//                      else{
              new ProducerRecord[String, Object](topic, 0, key.toString(), obj)
//                      }

            }
          }

          kafkaObjectsE match {
            case Left(err) =>
              ProducerMessage.passThrough[String, Object, (AtomicWrite, Option[Throwable])]((atomicWrite, Some(err)))
            case Right(kafkaObjects) =>
              val producerRecords = kafkaObjects.map(kafkaObjectToProducerRecord)
              ProducerMessage
                .multi[String, Object, (AtomicWrite, Option[Throwable])](producerRecords, (atomicWrite, None))
          }
        }
      )
      .via(Producer.flexiFlow(producerSettings, kafkaProducer))
      .map(_.passThrough match {
        case (aw, Some(x)) => (aw, Failure(x))
        case (aw, None) =>
          val seqNums = aw.payload.map(_.sequenceNr).toSet
          val highestSeqNr = seqNums.max

          replicator ! Update(HighestSeqNrKey(aw.persistenceId), GCounter.empty :+ highestSeqNr, WriteLocal) {
            currentHsn =>
              currentHsn :+ (highestSeqNr - currentHsn.value).toLong
          }

          (aw, Success(()))
      })
      .mergeSubstreams
      .runWith(Sink.seq)
      .map(_.sortBy(aw => messages.indexOf(aw._1)).map(_._2))
  }

  override def asyncDeleteMessagesTo(persistenceId: String, toSequenceNr: Long): Future[Unit] = {
    val topic = persistenceId + KafkaJournal.journalPostfix
    val partition = new TopicPartition(topic, 0)
    val subscription = Subscriptions.assignmentWithOffset(partition, 0)

    for {
      endOffset <- getEndOffset(partition)
      result <- Consumer
        .committableSource(consumerSettings, subscription)
        .takeWhile(_.record.offset < endOffset - 1, inclusive = true)
        .map(msg => (KafkaJournalKey(msg.record.key), msg))
        .filter { case (key, _) => toSequenceNr >= key.sequenceNr }
        .map {
          case (key, msg) =>
            ProducerMessage.single(new ProducerRecord[String, Object](topic, key.toString, null), msg.committableOffset)
        }
        .via(Producer.flexiFlow(producerSettings))
        .map(_.passThrough)
        .toMat(Committer.sink(committerSettings))(Keep.both)
        .mapMaterializedValue(DrainingControl.apply.tupled)
        .run()
        .streamCompletion
        .map { _ =>
          ()
        }
        .recoverWith {
          case _: RetriableCommitFailedException => asyncDeleteMessagesTo(persistenceId, toSequenceNr)
          case exception: Throwable              => throw exception
        }
    } yield result
  }

  override def asyncReadHighestSequenceNr(
    persistenceId: String,
    fromSequenceNr: Long,
  ): scala.concurrent.Future[Long] = {
    val hsnPromise = Promise[Long]()
    val hsnFuture = hsnPromise.future

    val hsnResult = replicator ? Get(HighestSeqNrKey(persistenceId), ReadLocal)
    hsnResult.andThen {
      case Success(c @ GetSuccess(_, _)) =>
        hsnPromise.success(c.get(HighestSeqNrKey(persistenceId)).value.toLong)

      case Success(c @ NotFound(key, _)) =>
        // This no-op ensures the counter exists and is initialized to zero.
        replicator ! Update(key, GCounter.empty, WriteLocal)(x => x)
        hsnPromise.success(0L)

      case Failure(exception) =>
        Console.println(exception)
        hsnPromise.failure(exception)
    }

    hsnFuture
  }

  override def asyncReplayMessages(persistenceId: String, fromSequenceNr: Long, toSequenceNr: Long, max: Long)(
    recoveryCallback: org.apache.pekko.persistence.PersistentRepr => Unit
  ): scala.concurrent.Future[Unit] = {
    val topic = persistenceId + KafkaJournal.journalPostfix
    val partition = new TopicPartition(topic, 0)
    val subscription = Subscriptions.assignmentWithOffset(partition, 0)
    for {
      endOffset <- getEndOffset(partition)
      result <- Consumer
        .plainSource[String, Object](consumerSettings, subscription)
        .takeWhile(_.offset < endOffset - 1, inclusive = true)
        .map(record => (KafkaJournalKey(record.key), record))
        .filter { case (key, _) => fromSequenceNr <= key.sequenceNr && toSequenceNr >= key.sequenceNr }
        .runWith(Sink.seq)
        /* In the event two records have the same sequence Id,
         * prefer the one with the largest offset.
         */
        .map(_.groupBy { case (key, _) => key.sequenceNr }.view.mapValues(_.maxBy {
          case (_, record) => record.offset
        }))
        .map {
          _.values.toList.sortBy { case (key, _) => key.sequenceNr }
        }
        .map(
          _.map {
            case (key, record) =>
              Console.println(s"replaying $record")

              ///// TODO should we uncomment the following to have support for timestamps during replay?
//            record.value match {
//              case genRecord: GenericRecord =>
//                val schema = genRecord.getSchema
//                val hasTimestamp = schema.getField("timestamp") != null
//                if (hasTimestamp) {
//                  val timeStamp = genRecord.get("timestamp") match {
//                    case None => record.timestamp
//                    case null => record.timestamp
//                    case a => a
//                  }
//                  genRecord.put("timestamp", timeStamp)
//                }
//              case _ => ()
//            }
              val deserializedObjectO = AnySerializedObjectToAvro(
                serializationExtension,
                key.serializerId,
                key.manifest,
                record.value,
              ) // TODO check if the schema contains a timestamp and do tehe reverse
              val (obj, isDeleted) = deserializedObjectO match {
                case None      => (null, true)
                case Some(obj) => (obj, false)
              }
              PersistentRepr(
                obj,
                key.sequenceNr,
                persistenceId,
                key.manifest,
                deleted = isDeleted,
                writerUuid = key.writerUuid.toString,
              )
          }.foldLeft((0, List.empty[PersistentRepr])) {
            case ((counter, accum), perst) =>
              // don't count deleted messages in the max
              if (perst.deleted) (counter, accum :+ perst)
              else if (counter >= max) (counter, accum)
              else (counter + 1, accum :+ perst)
          }._2
            .foreach(recoveryCallback)
        )
    } yield result
  }
}
