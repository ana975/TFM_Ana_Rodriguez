package org.example

import org.apache.spark.sql.SparkSession

object SparkSQL extends App {
  def apply(filename: String) = ???


  def execute(input: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark SQL example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    runBasicDataFrameExample(spark)
    spark.stop()
  }
  def runBasicDataFrameExample(spark: SparkSession): Unit = {
    val df = spark.sql(Array[String])
    df.collect()
  }
}
