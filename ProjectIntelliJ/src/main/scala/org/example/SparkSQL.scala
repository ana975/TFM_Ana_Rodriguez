package org.example

import org.apache.spark.sql.SparkSession

object SparkSQL extends App {

  val spark = SparkSession
    .builder()
    .appName("Spark SQL example")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()

  def execute(arg: String): Unit = {
    val df = spark.sql(arg)
    df.collect()
  }
}
