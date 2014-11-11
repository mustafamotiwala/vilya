package com.entity5.vilya

/**
 * Created by mabdullah on 4/6/14.
 */

import java.io.File

import com.entity5.vilya.api._
import com.entity5.vilya.util.ConfigSupport
import io.undertow.server.handlers.resource.{FileResourceManager, ClassPathResourceManager}
import io.undertow.servlet.Servlets.{deployment, defaultContainer, servlet}
import io.undertow.Handlers.{path, resource}
import io.undertow.Undertow
import akka.actor.ActorSystem
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions._

object Server extends ConfigSupport {
  def main(args: Array[String]): Unit = {
    val log = LoggerFactory.getLogger(this.getClass)
    sys.props put("org.scalatra.environment", config.getString("run.mode"))
    val classLoader = getClass.getClassLoader
    val apiServlet = servlet("API Endpoint Servlet", classOf[ApplicationServlet]) addMapping "/vilya/*" setAsyncSupported true

    val deploymentInfo = deployment setClassLoader classLoader setContextPath "" setDeploymentName "Vilya"
    deploymentInfo.addServlet(apiServlet)
    deploymentInfo.addServletContextAttribute("actorSystem", ActorSystem("Vilya"))
    deploymentInfo.getServletContextAttributes.foreach((t) => log.info(s"${t._1} -> ${t._2}"))

    log.info("Done spitting out context attributes")
    val container = defaultContainer.addDeployment(deploymentInfo)
    container.deploy()

    val apiHandler = container.start()
    val pathHandler = path()
    val indexFile = new ClassPathResourceManager(classLoader, "webapp/index.html")
    val rootDir = new ClassPathResourceManager(classLoader, "webapp")
    pathHandler addPrefixPath ("/api", apiHandler)
    pathHandler.addPrefixPath("/assets", resource(new ClassPathResourceManager(classLoader, "webapp/assets/")))
    pathHandler.addExactPath("/", resource(indexFile))
    pathHandler.addExactPath("/login", resource(indexFile))


    //    val server = Undertow.builder.addHttpListener(config.getInt("http.port"), config.getString("http.interface")).setHandler(apiHandler).build()
    val server = Undertow.builder.addHttpListener(config.getInt("http.port"), config.getString("http.interface")).setHandler(pathHandler).build()
    log.info("Server started on: {}:{}",config.getString("http.interface"), config.getInt("http.port"))
    server.start()
  }
}
