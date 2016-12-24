package com.example.cassandraviadatastax.repository

import com.datastax.driver.core.ResultSet
import com.datastax.driver.core.querybuilder.{QueryBuilder}
import com.example.cassandraviadatastax.application.Configuration.{hostname, keyspace, password, port, username}
import com.example.cassandraviadatastax.application.Configuration.{fieldAddress, fieldEmail, fieldId, fieldMobile, fieldName}
import com.example.cassandraviadatastax.application.Configuration.{consistencyLevel, tableUser}
import com.example.cassandraviadatastax.model.User

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Michael on 20.12.2016.
  */
class SimpleCassandraRepositoryForTest {

  val session = new CassandraSession(keyspace, hostname, port, username, password)

  val select = QueryBuilder.select().from(tableUser)
  select.where(QueryBuilder.eq(fieldId, QueryBuilder.bindMarker("id")))
  val selectStatement = session.prepare(select.toString)

  val delete = QueryBuilder.delete().from(tableUser)
  delete.where(QueryBuilder.eq(fieldId, QueryBuilder.bindMarker("id")))
  val deleteStatement = session.prepare(delete.toString)


  def cassandraSelectById(id: Int) = {
    val bindStatement = selectStatement.bind
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

  def select(id: Int): ArrayBuffer[User] = {
    val users = new ArrayBuffer[User]
    cassandraSelectById(id).get.all().forEach(f => users += User(f.getInt(fieldId), f.getString(fieldName),
      f.getString(fieldEmail), f.getString(fieldMobile), f.getString(fieldAddress)))
    users
  }

  def delete(id: Int): ResultSet = {
    val bindStatement = deleteStatement.bind
    bindStatement.setInt("id", id)
    bindStatement.setConsistencyLevel(consistencyLevel)

    session.execute(bindStatement)
  }

  def shutdown() = {
    session.shutdownAsync().get
    session.session.getCluster.closeAsync().get
  }
}

object SimpleCassandraRepositoryForTest {
  def apply() = new SimpleCassandraRepositoryForTest
}