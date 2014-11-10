package com.entity5.phonex

import javax.servlet.http.HttpServletRequest

import _root_.akka.actor.ActorSystem
import org.scalatra._
import org.slf4j._

trait VliyaStack extends ScalatraServlet{
  def actorSystem = {
    servletContext.getAttribute("actorSystem").asInstanceOf[ActorSystem]
  }

  notFound {
    // remove content type in case it was set through an action
    contentType = null
    serveStaticResource() getOrElse resourceNotFound()
  }

  override def requestPath(implicit request: HttpServletRequest) = {
    Option(request.getPathInfo) match{
      case Some(path) => path
      case None => ""
    }
  }

}

trait OAuth2AuthenticatedResource extends ScalatraServlet {
  before() {
    /*========================================*/
    /*==  Process the authorizaion header   ==*/
    /*========================================*/
    val authHeader = request getHeader "Authorization"
  }
}

trait BasicAuthSupport extends VliyaStack with ScalatraBase{
  private val log = LoggerFactory.getLogger(this.getClass)
  val basicAuthPattern = "(?i)^Basic ?(.*)$".r
  before() {
    val authHeader = request getHeader "Authorization"
    authHeader match{
      case basicAuthPattern(encodedCredentials) => {
        log.debug("Received Authentication Header: {}", encodedCredentials)
        val creds = new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(encodedCredentials))
        log.debug("Credentials received: {}", creds)
        if(creds != "davosec:freeswitch"){
          authenticationFailure
        }
      }
      case _ => {
        authenticationFailure
      }
    }
  }

  def authenticationFailure = {
    response setHeader("WWW-Authenticate", "Basic Realm=PhoneX")
    halt (401, "Unauthenticated")
  }
}
