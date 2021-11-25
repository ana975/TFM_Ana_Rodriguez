package org.example

import org.apache.commons.lang3.BooleanUtils.or
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.example.SparkSQL.execute

import scala.collection.JavaConverters._
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}



object Zookeepers {

  val retryPolicy = new ExponentialBackoffRetry(3000, 3)
  val client: CuratorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy)
  client.start()
  val zk = client.getZookeeperClient.getZooKeeper

    def zookeeper(msg: String): Unit = {
      val result = msg.startsWith("CREATE TABLE")

      if (result) {

        var pos = msg.indexOf("USING")
        pos = pos - 1
        val pathname = msg.substring(13, pos)

        val pqt = msg.startsWith("CREATE TABLE " + pathname + " USING parquet")
        val pqt2 = msg.startsWith("CREATE TABLE " + pathname + " USING org.apache.spark.sql.parquet")
        if (pqt) {
          zk.setData("hdfs://127.0.0.1:8020/" + pathname, msg.getBytes(), -1)
        } else if (pqt2) {
          zk.setData("hdfs://127.0.0.1:8020/" + pathname, msg.getBytes(), -1)
        } else {
          zk.setData("/tables/" + pathname, msg.getBytes(), -1)
        }

      }

      val drop = msg.startsWith("DROP TABLE")
      if (drop) {
        val name = msg.substring(11)
        zk.delete("/tables/"+ name, -1)
      }
    }

    def readzookeeper(): Unit = {
      Try(zk.getChildren("/tables",false)) match {
        case Success(ans) =>
          var info = ""
          for (node <- ans.asScala) {
            info = info + zk.getData("/tables/" + node, false, null).toString()
            execute(info)
          }
        case Failure(ans)=>
          List[String]()
      }
    }
}
