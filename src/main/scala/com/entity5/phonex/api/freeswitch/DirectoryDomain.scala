package com.entity5.phonex.api.freeswitch

/**
 * Created by mabdullah on 5/24/14.
 */
case class DirectoryDomain(name: String, groups:List[Group], users:List[User]){
  def toXml = <domain name={name}>
    <groups>
      {groups.map(_.toXml)}
    </groups>
    <users>
      {users.map(_.toXml)}
    </users>
  </domain>
}

object DirectoryDomain {
  def testDirectoyDomain = {
    new DirectoryDomain("entity5.net",
      new Group("e5",
        new User("mustafa",new Param("password","bubblegum")::Nil,
          Variable("user_context","entity5")::
            Variable("effective_caller_id","Mustafa Abdullah")::
            Variable("effective_caller_id_number", "1001")::
            Nil
        )::Nil
      )::Nil, Nil
    )
  }
}
