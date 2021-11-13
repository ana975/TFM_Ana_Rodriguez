package org.example

import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry


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
        zk.setData(pathname, msg.getBytes(), -1)
      }
    }
}
