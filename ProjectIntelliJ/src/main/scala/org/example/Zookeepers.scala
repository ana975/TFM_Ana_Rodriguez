package org.example

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
        val pathname = msg.substring(13, pos).trim
        if (result) {
          client.create().creatingParentsIfNeeded().forPath("/tables/" + pathname)
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
          for (node <- ans.asScala) {
            val info = new String(zk.getData("/tables/" + node, false, null))
            execute(info)
          }
        case Failure(ans)=>
          List[String]()
      }
    }
}
