package com.entity5.phonex.api.freeswitch

/**
 * Created by mabdullah on 5/21/14.
 */
case class SofiaConfiguration(name:String, globalSettings:List[Param], profiles:List[Profile]) {
  def toXml = {
    <?xml version="1.0" encoding="UTF-8" standalone="no"?>
    <configuration name={name}>
      <global_settings>
        {globalSettings.map(_.toXml)}
      </global_settings>
      <profiles>
        {profiles.map(_.toXml)}
      </profiles>
    </configuration>
  }
}

object SofiaConfiguration {
  val testConfiguration = {
    val globalSettings = Param("log-level", "0")::Param("debug-presence", "0")::Nil
    val settings =
      Param("debug", "0")::
        Param("sip-trace","no")::
        Param("sip-capture","no")::
        Param("rfc2833-pt","101")::
        Param("sip-port","5060")::
        Param("dialplan","XML")::
        Param("context","public")::
        Param("dtmf-duration","2000")::
        Param("inbound-codec-prefs","G722,PCMU,PCMA,GSM")::
        Param("outbound-codec-prefs","PCMU,PCMA,GSM")::
        Param("hold-music","local_stream://moh")::
        Param("rtp-timer-name", "soft")::
        Param("local-network-acl","localnet.auto")::
        Param("manage-presence","false")::
        Param("inbound-codec-negotiation","generous")::
        Param("nonce-ttl","60")::
        Param("auth-calls","false")::
        Param("inbound-late-negotiation","true")::
        Param("inbound-zrt-passthru","true")::
        Param("rtp-ip","192.168.56.10")::
        Param("sip-ip","192.168.56.10")::
        Param("ext-rtp-ip","auto-nat")::
        Param("ext-sip-ip","auto-nat")::
        Param("rtp-timeout-sec","300")::
        Param("rtp-hold-timeout-sec","1800")::
        Param("enable-3pcc","true")::
        Param("apply-nat-act","nat.auto")::
        Param("apply-inbound-acl","domains")::
        Param("local-network-acl","localnet.auto")::
        Nil
    val profile = Profile("default", Nil, Nil, ProfileDomain("all", false, false)::Nil, settings)
    new SofiaConfiguration("sofia.conf", globalSettings, profile::Nil )
  }

  val internalConfiguration = {
    val globalSettings = Param("log-level", "0")::Param("debug-presence", "0")::Nil
    val settings =
      Param("debug", "0")::
        Param("sip-trace","no")::
        Param("sip-capture","no")::
        Param("rfc2833-pt","101")::
        Param("sip-port","5060")::
        Param("dialplan","XML")::
        Param("context","public")::
        Param("dtmf-duration","2000")::
        Param("inbound-codec-prefs","G722,PCMU,PCMA,GSM")::
        Param("outbound-codec-prefs","PCMU,PCMA,GSM")::
        Param("hold-music","local_stream://moh")::
        Param("rtp-timer-name", "soft")::
        Param("local-network-acl","localnet.auto")::
        Param("manage-presence","false")::
        Param("inbound-codec-negotiation","generous")::
        Param("nonce-ttl","60")::
        Param("auth-calls","false")::
        Param("inbound-late-negotiation","true")::
        Param("inbound-zrt-passthru","true")::
        Param("rtp-ip","$${local_ip_v4}")::
        Param("sip-ip","$${local_ip_v4}")::
        Param("ext-rtp-ip","auto-nat")::
        Param("ext-sip-ip","auto-nat")::
        Param("rtp-timeout-sec","300")::
        Param("rtp-hold-timeout-sec","1800")::
        Param("enable-3pcc","true")::
        Param("apply-nat-act","nat.auto")::
        Param("apply-inbound-acl","domains")::
        Param("local-network-acl","localnet.auto")::
        Nil
    val profile = Profile("internal", Nil, Nil, ProfileDomain("all", false, false)::Nil, settings)
    new SofiaConfiguration("sofia.conf", globalSettings, profile::Nil )
  }
}


/*
    <param name="rtp-ip" value="$${local_ip_v4}"/>
    <param name="sip-ip" value="$${local_ip_v4}"/>
    <param name="ext-rtp-ip" value="auto-nat"/>
    <param name="ext-sip-ip" value="auto-nat"/>
    <param name="rtp-timeout-sec" value="300"/>
    <param name="rtp-hold-timeout-sec" value="1800"/>
    <!--<param name="enable-3pcc" value="true"/>-->
 */