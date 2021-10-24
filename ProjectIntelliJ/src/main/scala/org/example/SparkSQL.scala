package org.example

import org.apache.spark.sql.SparkSession


object SparkSQL extends App {

  def mains(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark SQL example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    runBasicDataFrameExample(spark)
    spark.stop()
  }

  private def runBasicDataFrameExample(spark: SparkSession): Unit = {

    val df = spark.read.json("/user/client")
    df.createOrReplaceTempView("querySQL")
    val sqlDF = spark.sql("SELECT * FROM querySQL")
    sqlDF.show()
  }
}