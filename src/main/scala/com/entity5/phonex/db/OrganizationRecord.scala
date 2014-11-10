package com.entity5.phonex.db

import com.datastax.driver.core.Row
import com.entity5.phonex.domain.Organization
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._


/**
 * Created by mabdullah on 9/1/14.
   organizationName String,
   groups Set<String>,
   didNumbers Set<String>,
   primaryContactName String,
   primaryContactEmail String,
   primaryContactCell String

 */
class OrganizationRecord extends CassandraTable[OrganizationRecord, Organization ]{

  object domain extends StringColumn(this) with PrimaryKey[String]
  object organizationName extends StringColumn(this)
  object groups extends SetColumn[OrganizationRecord, Organization, String](this)
  object didNumbers extends SetColumn[OrganizationRecord, Organization, String](this)
  object contactName extends OptionalStringColumn(this)
  object contactNumber extends OptionalStringColumn(this)
  object contactEmail extends OptionalStringColumn(this)

  override def fromRow(r: Row) = Organization(domain(r), organizationName(r), groups(r), didNumbers(r), contactName(r), contactEmail(r), contactNumber(r))
}
