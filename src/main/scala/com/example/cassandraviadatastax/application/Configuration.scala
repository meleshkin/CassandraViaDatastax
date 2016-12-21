package com.example.cassandraviadatastax.application

import com.datastax.driver.core.ConsistencyLevel

/**
  * Created by Michael on 21.12.2016.
  */
object Configuration {
  val keyspace = "users"
  val hostname = "127.0.0.1"
  val port = 9042
  val username = ""
  val password = ""
  val consistencyLevel = ConsistencyLevel.ALL

  val tableUser = "User"

  val fieldId = "id"
  val fieldName = "name"
  val fieldEmail = "email"
  val fieldMobile = "mobile"
  val fieldAddress = "address"
}
