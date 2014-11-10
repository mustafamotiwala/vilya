package com.entity5.phonex.api

import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import com.entity5.phonex._
import com.entity5.phonex.api.freeswitch.SofiaConfiguration
import com.entity5.phonex.domain.User
import com.entity5.phonex.service.{FindUser, UserService}
import org.scalatra._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

/**
 * All service within this service will respond with a content-type of application/xml
 */
class FreeSwitchServlet extends VliyaStack with BasicAuthSupport with FutureSupport {
  private val log = LoggerFactory.getLogger(classOf[FreeSwitchServlet])
  implicit val actorResponseTimeout = Timeout(10.seconds)
  implicit val executor = ExecutionContext.Implicits.global //ExecutionContext fromExecutorService Executors.newFixedThreadPool(2)

  val noConfig = {
    <document type="freeswitch/xml">
      <section name="result">
        <result status="not found"/>
      </section>
    </document>
  }

  def logRequestParameters() {
    val parameters = request.getParameterNames
    while (parameters.hasMoreElements){
      val key = parameters.nextElement()
      log.info("{} -> {}", key::request.getParameter(key)::Nil:_*)
    }
  }

  get("/?") {
    log info ("Request Received: {}", request)
    new AsyncResult() {
      override val is: Future[_] ={
        contentType = "text/xml"
        val userService = actorSystem.actorOf(Props[UserService])
        val user = (userService ? FindUser(params.get("username").getOrElse(halt(400)))).mapTo[Future[Seq[User]]].flatMap(x=>x)
        user.map(seq => seq.foreach(log.debug("Results from DB: {}",_)))
        user.map(_(0).toFreeSwitchXml)
      }
    }
  }

  /*
   *Request for cdr_csv: [hostname=freeswitch&section=configuration&tag_name=configuration&key_name=name&key_value=cdr_csv.conf]
   */
  post("/configuration") {
    val section = params("section")
    val keyName = params("key_name")
    val keyValue = params("key_value")
    log info ("Received request for section: {}, object: {} -> {}", section,keyName,keyValue)
    log info "======================  BEGIN  ======================================="
    logRequestParameters()
    log info "======================== END ========================================="
    //    val userService = actorSystem.actorOf(Props[UserService])
    new AsyncResult() {
      override val is = {
        Future {
          contentType = "text/xml"
          (keyName, keyValue) match {
            case ("name","sofia.conf") => {
              val profile = params.get("profile")
              <document type="freeswitch/xml">
                <section name={section}>
                  { SofiaConfiguration.testConfiguration.toXml }
                </section>
              </document>
            }
            case _ => noConfig
          }
        }
      }
    }
  }

  post("/dialplan") {
    val section = params("section")
    val keyName = params("key_name")
    val keyValue = params("key_value")
    log info ("Dialplan request for section: {}, object: {} -> {}", section,keyName,keyValue)
    log info "======================  BEGIN  ======================================="
    logRequestParameters()
    log info "======================== END ========================================="
    //    val userService = actorSystem.actorOf(Props[UserService])
    new AsyncResult() {
      override val is = {
        Future {
          contentType = "text/xml"
          (keyName, keyValue) match {
            case ("name","sofia.conf") => {
              val profile = params.get("profile")
              <document type="freeswitch/xml">
                <section name={section}>
                  { SofiaConfiguration.testConfiguration.toXml }
                </section>
              </document>
            }
            case _ => noConfig
          }
        }
      }
    }
  }

  post("/directory") {
    log.info("Request received from Freeswitch: {}", request.getQueryString)
    val userService = actorSystem actorOf Props[UserService]
    val section = params("section")
    val user = params.get("user").getOrElse(halt(404))
    val domain = params.get("domain").getOrElse(halt(404))
    log info ("Received request for section: {}, User: {}@{}", section,user,domain)
    //    val userService = actorSystem.actorOf(Props[UserService])
    contentType = "text/xml"

    val userOption = (userService ? FindUser(user)).mapTo[Future[Seq[User]]]

    userOption recover {
      case _ => noConfig
    }

//    (user,domain ) match {
//      case (Some(_),"entity5.net") => {
//        val profile = params.get("profile")
//        <document type="freeswitch/xml">
//          <section name={section}>
//            {DirectoryDomain.testDirectoyDomain.toXml}
//          </section>
//        </document>
//      }
//      case _ => notFoundConfig
//    }
  }

  notFound {
    log.warn("Unable to handle request... no such path {}", request)
    response.setStatus(404)
    noConfig
  }
}
