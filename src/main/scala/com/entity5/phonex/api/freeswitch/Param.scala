package com.entity5.phonex.api.freeswitch

/**
 * Created by mabdullah on 5/21/14.
 */

case class Param(name: String, value: String) {
  def toXml = <param name={name} value={value} />
}
