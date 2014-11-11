package com.entity5.vilya.api

import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import com.entity5.vilya._
import org.scalatra._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class ApplicationServlet extends VliyaStack with BasicAuthSupport with FutureSupport {
  private val log = LoggerFactory.getLogger(classOf[ApplicationServlet])
  implicit val actorResponseTimeout = Timeout(10.seconds)
  implicit val executor = ExecutionContext.Implicits.global //ExecutionContext fromExecutorService Executors.newFixedThreadPool(2)

  get("/?") {
    log info ("Request Received: {}", request)
    new AsyncResult() {
      override val is: Future[_] = {
        contentType = "text/xml"
        Future apply {
          <hello>world!</hello>
        }
      }
    }
  }

  notFound {
    log.warn("Unable to handle request... no such path {}", request)
    response setStatus 404
  }

  override def authenticate(username: String, password: String) = {
    Future.apply(true)
  }
}
