package com.example.cassandraviadatastax.repository

import java.util.concurrent.{Future, TimeUnit}

import com.datastax.driver.core.{Cluster, RegularStatement, Statement}

/**
  * Created by Michael on 19.12.2016.
  */
class CassandraSession(keyspace: String, hostname: String, port: Int, username: String, password: String) {
  val builder = Cluster.builder().withPort(port).addContactPoint(hostname).withCredentials(username,password)
  val session = builder.build().connect(keyspace)

  def prepare(statement: String) = session.prepare(statement)
  def prepare(statement: RegularStatement) = session.prepare(statement)
  def executeAsync(statement: Statement) = session.executeAsync(statement)
  def execute(statement: Statement) = session.execute(statement)
  def execute(statement: String) = session.execute(statement)

  def shutdownAsync() = {
    val future = session.closeAsync
    new Future[Void] {
      override def get(): Void = future.get()
      override def get(timeout: Long, unit: TimeUnit): Void = future.get(timeout, unit)
      override def cancel(mayInterruptIfRunning: Boolean): Boolean = future.cancel(mayInterruptIfRunning)
      override def isDone: Boolean = future.isDone
      override def isCancelled: Boolean = future.isCancelled
    }
  }

  def shutdown() =  {
    session.close
    session.getCluster.close
  }
}
