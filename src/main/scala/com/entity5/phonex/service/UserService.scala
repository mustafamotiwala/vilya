package com.entity5.phonex.service

import akka.actor.Actor
import com.entity5.phonex.db.Users
import com.entity5.phonex.domain.User
import com.entity5.phonex.util.ConfigSupport
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by mabdullah on 4/30/14.
 */

case class FindUser(username: String)
case class AddUser(u:User)

case class Authenticate(name: String, password:String)

class UserService extends Actor with ConfigSupport {

  private val log = LoggerFactory.getLogger(this.getClass)

  override def receive = {
    case msg:FindUser => sender ! findUser(msg.username)
    case msg:AddUser => sender ! addUser(msg.u)
  }

  def findUser(username: String)={
    val user = Users.findByUsernameAndDomain(username)
    user.onSuccess{
      case x => x.foreach(log.debug("Initial data-set: {}",_))
    }
    user.onFailure{
      case ex => ex.printStackTrace()
    }
    user
  }

  def addUser(u:User) = {
    u
  }
}
