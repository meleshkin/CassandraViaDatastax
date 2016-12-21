package com.example.cassandraviadatastax.application

import com.example.cassandraviadatastax.repository.SimpleCassandraRepositoryForTest

/**
  * Created by Michael on 21.12.2016.
  */
object Application extends App {
  val repo = new SimpleCassandraRepositoryForTest
  println(repo.selectById(1))
}
