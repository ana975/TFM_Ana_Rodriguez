package org.example


import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.cluster.client.ClusterClient.Send
import org.apache.spark.sql.SparkSession


object SparkSQL {

  case class database(msg: String)

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark SQL example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    runBasicDataFrameExample(spark)
    spark.stop()
  }

  def runBasicDataFrameExample(spark: SparkSession): Unit = {

    val df = spark.sql("example/user/client/database.sql")
    df.createOrReplaceTempView("querySQL")
    val sqlDF = spark.sql("SELECT * FROM querySQL")
    sqlDF.show()

  }

  class ClientSpark(clientSpark: ActorRef) extends Actor with ActorLogging {
    var sparkSQL: ActorRef = _
    def receive: Receive = {
      case database(sqlDF) =>
        sparkSQL = sender()
        clientSpark ! Send("/user/master",sqlDF, localAffinity = false)
    }
  }
}

