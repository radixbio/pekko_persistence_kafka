package com.radix.shared.persistence.query

import org.apache.pekko.actor.ExtendedActorSystem
import com.typesafe.config.Config
import org.apache.pekko.persistence.query.ReadJournalProvider

class KafkaReadJournalProvider(system: ExtendedActorSystem, config: Config) extends ReadJournalProvider {
  override def scaladslReadJournal(): KafkaScalaReadJournal = new KafkaScalaReadJournal(system, config)

  override def javadslReadJournal(): KafkaJavaReadJournal = new KafkaJavaReadJournal(scaladslReadJournal())
}
