package org.example

import org.apache.spark.sql.{Row, SparkSession}

object SparkSQL {

  val spark = SparkSession
    .builder()
    .appName("Spark SQL")
    .master("spark://HOST:2551")
    .getOrCreate()

  def execute(arg: String): Array[Row] = {
    val df = spark.sql(arg)
    df.collect()
  }
}
