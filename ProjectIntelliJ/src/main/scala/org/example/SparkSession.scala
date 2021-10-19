package org.example

object SparkSession {

  import org.apache.spark.sql.SparkSession

  val session = SparkSession.builder()
    .constructor()
    .appName ("Session spark")
    .config ()
    .getOrCreate ()
}
