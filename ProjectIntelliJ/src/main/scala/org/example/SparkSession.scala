package org.example

import org.apache.spark.sql.SparkSession

object SparkSession extends App {

  val spark = SparkSession
    .builder()
    .appName ("Session spark")
    .config ()
    .getOrCreate ()

}
