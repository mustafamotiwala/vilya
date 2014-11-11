package com.entity5.vilya

import javax.servlet.http.HttpServletRequest

import akka.actor.ActorSystem
import org.scalatra._
import org.slf4j._
import scala.concurrent.duration._

import scala.concurrent.{Future, Await}

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

trait OAuth2Resource extends ScalatraServlet {
  val bearerTokenPattern = "(?i)^Bearer (.*)$".r
  before() {
    /* ======================================== */
    /* ==  Process the authorizaion header   == */
    /* ======================================== */
    request getHeader "Authorization" match {
      case bearerTokenPattern(accessToken) => validateAccessToken(accessToken)
      case _ => halt(401, "Unauthenticated")
    }
  }

  def validateAccessToken(token: String):Boolean
}

trait BasicAuthSupport extends VliyaStack with ScalatraBase{
  private val log = LoggerFactory.getLogger(this.getClass)
  val basicAuthPattern = "(?i)^Basic ?(.*)$".r
  before() {
    val authHeader = request getHeader "Authorization"
    authHeader match{
      case basicAuthPattern(encodedCredentials) => {
        val creds = new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(encodedCredentials)).split(":")
        // If we can't authenticate in 500 ms; we're screwed anyway.
        // Its okay to wait, can't let the processing continue unless user is authenticated.
        if(!Await.result(authenticate(creds(0), creds(1)), 500 milliseconds)){
          authenticationFailure
        }
      }
      case _ => {
        authenticationFailure
      }
    }
  }

  def authenticate(username: String, password: String):Future[Boolean]

  def authenticationFailure = {
    response setHeader("WWW-Authenticate", "Basic Realm=PhoneX")
    halt (401, "Unauthenticated")
  }
}
