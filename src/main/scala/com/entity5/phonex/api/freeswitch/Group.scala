package com.entity5.phonex.api.freeswitch

/**
 * Created by mabdullah on 5/24/14.
 */
case class Group(name: String, users:List[User]) {
  def toXml = <group name={name}>
    <users>
      {users.map (_.toXml)}
    </users>
  </group>
}
