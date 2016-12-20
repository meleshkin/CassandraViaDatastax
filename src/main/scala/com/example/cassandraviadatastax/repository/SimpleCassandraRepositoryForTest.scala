package com.example.cassandraviadatastax.repository

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.querybuilder.{QueryBuilder, Select}
import com.example.cassandraviadatastax.model.User

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Michael on 20.12.2016.
  */
class SimpleCassandraRepositoryForTest {
  val keyspace = "users"
  val hostname = "127.0.0.1"
  val port = 9042
  val username = ""
  val password = ""
  val session = new CassandraSession(keyspace, hostname, port, username, password)

  val select = QueryBuilder.select().from("User")
  select.where(QueryBuilder.eq("id", QueryBuilder.bindMarker("id")))
  val statement = session.prepare(select.toString)

  def cassandraSelect(id: Int) = {
    val bindStatement = statement.bind
    bindStatement.setInt("id", id)
    bindStatement.setConsistencyLevel(ConsistencyLevel.ALL)

    session.executeAsync(bindStatement)
  }

  def selectById(id: Int) = {
    val users = new ArrayBuffer[User]
    cassandraSelect(id).get.all().forEach(f => users += User(f.getInt("id"), f.getString("name"), f.getString("email"), f.getString("mobile"), f.getString("address")))
    users
  }
}

object SimpleCassandraRepositoryForTest extends App {
  val repo = new SimpleCassandraRepositoryForTest
  println(repo.selectById(1))
}