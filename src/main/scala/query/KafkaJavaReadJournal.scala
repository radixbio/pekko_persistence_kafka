package com.radix.shared.persistence.query

import org.apache.pekko.NotUsed
import org.apache.pekko.persistence.query.javadsl
import org.apache.pekko.stream.javadsl.Source

class KafkaJavaReadJournal(kafkaScalaReadJournal: KafkaScalaReadJournal)
    extends javadsl.ReadJournal
    with javadsl.CurrentPersistenceIdsQuery {

  override def currentPersistenceIds(): Source[String, NotUsed] = kafkaScalaReadJournal.currentPersistenceIds().asJava
}
