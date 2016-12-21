package com.example.cassandraviadatastax.repository

import com.datastax.driver.core.querybuilder.{QueryBuilder, Select}
import com.example.cassandraviadatastax.application.Configuration.{keyspace, hostname, username, password, port}
import com.example.cassandraviadatastax.application.Configuration.{fieldId, fieldName, fieldEmail, fieldMobile, fieldAddress}
import com.example.cassandraviadatastax.application.Configuration.{tableUser, consistencyLevel}
import com.example.cassandraviadatastax.model.User

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Michael on 20.12.2016.
  */
class SimpleCassandraRepositoryForTest {

  val session = new CassandraSession(keyspace, hostname, port, username, password)

  val select = QueryBuilder.select().from(tableUser)
  select.where(QueryBuilder.eq(fieldId, QueryBuilder.bindMarker("id")))
  val statement = session.prepare(select.toString)


  def cassandraSelect(id: Int) = {
    val bindStatement = statement.bind
    bindStatement.setInt("id", id)
    bindStatement.setConsistencyLevel(consistencyLevel)

    session.executeAsync(bindStatement)
  }

  def insert(user: User) = {
    val insert = QueryBuilder.insertInto(tableUser)
      .value(fieldId, user.id)
      .value(fieldName, user.name)
      .value(fieldEmail, user.email)
      .value(fieldMobile, user.mobile)
      .value(fieldAddress, user.address)

    session.execute(insert.toString)
  }

  def selectById(id: Int) = {
    val users = new ArrayBuffer[User]
    cassandraSelect(id).get.all().forEach(f => users += User(f.getInt(fieldId), f.getString(fieldName),
      f.getString(fieldEmail), f.getString(fieldMobile), f.getString(fieldAddress)))
    users
  }
}

object SimpleCassandraRepositoryForTest {
  def apply() = new SimpleCassandraRepositoryForTest
}