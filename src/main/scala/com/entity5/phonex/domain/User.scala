package com.entity5.phonex.domain

/**
 * Created by mabdullah on 6/11/14.
 */
case class User(login: String, sipPassword: Option[String], vmPassword: Option[String], name: String, email: Option[String], domain: String,
                extension: Option[Int], groups: Set[String]) extends FreeSwitchBase {

  override def toFreeSwitchXml = {
    <domain name={domain}>
      <groups>
        {groups.map((s) => <group name={s}>
          <users>
            <user id={login} type="pointer"></user>
          </users>
        </group>)}
      </groups>
      <users>
        <user id={login}>
          <params>
            {
              sipPassword match {
                case Some(p) => <param name="password" value={p}/>
                case _ => Nil
              }
              vmPassword match {
                case Some(p) => <param name="vm-password" value={p}/>
                case _ => Nil
              }
            }
          </params>
          <variables>
            <variable name="user_context" value={domain}/>
            <variable name="effective_caller_id_name" value={name} />
            {
              extension match {
                case Some(ext) => <variable name="effective_caller_id_number" value={ext.toString} />
                case _ => Nil
              }
            }
          </variables>
        </user>
      </users>
    </domain>
  }
}
