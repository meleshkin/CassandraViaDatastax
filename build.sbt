name := "CassandraViaDatastax"

version := "1.0"

scalaVersion := "2.12.1"

val CassandraDriverVersion = "3.0.1"

libraryDependencies ++= Seq(
  "com.datastax.cassandra" % "cassandra-driver-core" % CassandraDriverVersion
)