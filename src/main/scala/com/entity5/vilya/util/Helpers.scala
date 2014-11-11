package com.entity5.vilya.util

import com.datastax.driver.core.Cluster
import com.typesafe.config.ConfigFactory
import org.joda.time.{LocalDate, LocalDateTime}
import scala.concurrent.blocking

import scala.language.implicitConversions

object DateUtils{
  implicit def joda2java(ld: LocalDate) =  ld.toDate
  implicit def joda2java(ld: LocalDateTime) =  ld.toDate

  implicit class WrappedDate(val date: java.util.Date){
    def toLocalDate = {
      new LocalDate(date.getTime)
    }
  }

  implicit class WrappedLocalDate(val ld: LocalDate){
    def toDate = new java.util.Date(ld.toDate.getTime)
  }

}

trait ConfigSupport {
  protected val config = ConfigFactory.load()
}

object DatabaseSupport extends ConfigSupport{

  lazy val cluster = Cluster.builder
    .addContactPoint(config.getString("database.host"))
    .withCredentials(config.getString("database.username"), config.getString("database.password"))
    .withoutJMXReporting()
    .withoutMetrics()
    .build()

  lazy val session = blocking {
    cluster.connect(config.getString("database.keyspace"))
  }
}