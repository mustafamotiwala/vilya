package com.entity5.phonex.db


import com.datastax.driver.core.{Session, Row}
import com.entity5.phonex.domain.User
import com.entity5.phonex.util.PhoneXDb
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.column.SetColumn
import com.websudos.phantom.keys.PartitionKey

/**
 * Created by mabdullah on 8/30/14.
 */

sealed class Users extends CassandraTable[Users, User] {
  object username extends StringColumn(this) with PrimaryKey[String]
  object domain extends StringColumn(this)
  object fullName extends StringColumn(this)
  object email extends OptionalStringColumn(this)
  object sip_password extends OptionalStringColumn(this) //ClearText
  object voicemail_password extends OptionalStringColumn(this)
  object extension extends OptionalIntColumn(this)
  object groups extends SetColumn[Users, User, String](this)

  override def fromRow(r: Row) = User(username(r), sip_password(r), voicemail_password(r), fullName(r), email(r), domain(r), extension(r), groups(r))
}

object Users extends Users {
  implicit val cassandraSession:Session = PhoneXDb.session

  def findByUsernameAndDomain(username: String) = {
    select.where(_.username eqs username).fetch()
  }
}