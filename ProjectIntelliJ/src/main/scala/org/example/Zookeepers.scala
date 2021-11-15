package org.example

import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.example.SparkSQL.execute

import scala.collection.JavaConverters._



object Zookeepers {

  val retryPolicy = new ExponentialBackoffRetry(3000, 3)
  val client: CuratorFramework = CuratorFrameworkFactory.newClient(" 127.0.0.1:2181", retryPolicy)
  client.start()
  val zk = client.getZookeeperClient.getZooKeeper

    def zookeeper(msg: String): Unit = {
      val result = msg.startsWith("CREATE TABLE")
      if (result) {
        var pos = msg.indexOf("USING")
        pos = pos - 1
        val pathname = msg.substring(13, pos)
        zk.setData("/tables/" + pathname, msg.getBytes(), -1)
      }
      val drop = msg.startsWith("DROP TABLE")
      if (drop) {
        val name = msg.substring(11)
        zk.delete("/tables/"+ name, -1)
      }
    }
  
    def readzookeeper(): Unit = {
      val nodelist = zk.getChildren("/tables/",false)
      var info = ""
      for (node<- nodelist.asScala){
        info = info + zk.getData("/tables/" + node, false, null).toString()
      }
      execute(info)
    }
}
