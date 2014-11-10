package com.entity5.phonex.api.freeswitch

/**
 * Created by mabdullah on 5/21/14.
 */
case class Profile(name: String, gateways: List[Gateway], aliases: List[String], domains: List[ProfileDomain], settings: List[Param]) {
  def toXml = {
    <profile name={name}>
      <gateways>
        {gateways.map(_.toXml)}
      </gateways>
      <domains>
        {domains.map(_.toXml)}
      </domains>
      <settings>
        {settings.map(_.toXml)}
      </settings>
    </profile>
  }
}

case class ProfileDomain(name:String, alias: Boolean, parse: Boolean) {
  def toXml = {
    <domain name={name} alias={alias.toString} parse={parse.toString} />
  }
}