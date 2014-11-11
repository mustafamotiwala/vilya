package com.entity5.vilya.service

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.testkit.TestKit
import akka.util.Timeout
import com.entity5.vilya.domain.User
import com.entity5.vilya.util.ConfigSupport
import org.specs2.mutable._
import org.specs2.specification.Scope

import scala.concurrent.ExecutionContext


/**
 * Created by mabdullah on 6/8/14.
 */
class ActorSpec extends TestKit(ActorSystem("Test")) with Scope

class UserServiceSpec extends Specification with ConfigSupport {
  private val USERNAME = "test"
  private val DOMAIN = "example-domain.com"


  implicit val actorResponseTimeout = Timeout(500, TimeUnit.MILLISECONDS)
  implicit val executor = ExecutionContext.Implicits.global

  "The UserService Actor" should {
    "Return the queried user" in new ActorSpec with After{
      //Insert the test data
      insertTestData()

      //Get the actor ref/test subject
      val actorRef = system.actorOf(Props[UserService])
      val future = (actorRef ? FindUser(USERNAME)).mapTo[Option[User]]
      future.map{ data =>
        data.get must beLike {
//          case User(username, _,_,_,domain,_,_) if username == USERNAME && domain == DOMAIN => ok
          case _ => ko
        }
      }

//      val response = Await.result(future,Duration(1, TimeUnit.SECONDS))
//      //Tests
//      val result = response must beSome
//      val secondaryTestResult = response.get must beLike {
//        case User(username, _,_,_,domain,_,_) if username == USERNAME && domain == DOMAIN => ok
//        case _ => ko
//      }
//
//      result and secondaryTestResult

      //Cleanup database.
      def after = cleanupUser()

      def cleanupUser() {
//        Delete the test data from the keyspace - drop keyspace?
      }

      def insertTestData(){
        //Insert test data in the keyspace
      }
    }
  }
//  "The UserService Actor" should {
//    "Report users in groups" in new ActorSpec with After {
//      val groups = List(
//        MongoDBObject("name" -> "Sales Group", "users"->List(USERNAME)),
//        MongoDBObject("name" -> "Call Center", "users"->List("Dummy User", "Dummy User 2", "Dummy User 2")),
//        MongoDBObject("name" -> "Customer Support", "users"->List("Dummy User 4", "Dummy User 5", "Dummy User 6")),
//        MongoDBObject("name" -> "Technical Group", "users"->List(USERNAME, "Dummy User 4", "Dummy User 3"))
//      )
//      def after = ()
//    }
//  }
}
