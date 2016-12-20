package com.example.cassandraviadatastax.model

import scala.util.Random

/**
  * Created by Michael on 20.12.2016.
  */
case class User(val id: Int, val name: String, val email: String, val mobile: String, val address: String)

object User {
  val random = new Random
  def create(name: String, email: String, mobile: String, address: String) =
    new User(random.nextInt, name, email, mobile, address)
}


