package com.entity5.phonex.api.freeswitch

/**
 * Created by mabdullah on 5/24/14.
 */
case class Variable(name: String, value: String){
  def toXml = <variable name={name} value={value} />
}
