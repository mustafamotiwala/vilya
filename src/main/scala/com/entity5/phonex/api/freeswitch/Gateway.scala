package com.entity5.phonex.api.freeswitch

/**
 * Created by mabdullah on 5/21/14.
 */
case class Gateway(name: String, params: List[Param]) {
  def toXml = {
    <gateway name={name}>
      {params.map(_.toXml)}
    </gateway>
  }
}