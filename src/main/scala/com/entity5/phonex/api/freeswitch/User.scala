package com.entity5.phonex.api.freeswitch

/**
 * Created by mabdullah on 5/24/14.
 */
case class User(id:String, params: List[Param], variables: List[Variable]){
  def toXml = <user id={id}>
    <params>{params.map(_.toXml)}</params>
    <variables>{variables.map(_.toXml)}</variables>
  </user>
}
