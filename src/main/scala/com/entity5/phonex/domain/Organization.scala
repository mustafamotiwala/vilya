package com.entity5.phonex.domain

/**
 * Created by mabdullah on 9/1/14.
 */
case class Organization (domain: String, organizationName: String, groups: Set[String], didNumbers: Set[String],
                         contactName: Option[String], contactEmail: Option[String], contactNumber: Option[String])
