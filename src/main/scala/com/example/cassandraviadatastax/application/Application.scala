package com.example.cassandraviadatastax.application

import com.example.cassandraviadatastax.model.User
import com.example.cassandraviadatastax.repository.SimpleCassandraRepositoryForTest

/**
  * Created by Michael on 21.12.2016.
  */
object Application extends App {
  val newUser = User.create("User1", "user1@mailserver.com", "+7927XXXXXXX", "Any street")
  val repo = SimpleCassandraRepositoryForTest()
  repo.insert(newUser)
  println("User '" + newUser + "' successfully saved")
}
