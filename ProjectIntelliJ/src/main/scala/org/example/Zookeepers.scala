package org.example

import org.apache.zookeeper.{Watcher, ZooKeeper}


object Zookeepers {

  def zookeeper(msg: String): Unit = {
    var result = msg.startsWith("CREATE TABLE")
    case result = true =>
      val zk = new ZooKeeper("127.0.0.1:2181", 30000, Watcher)
      zk.setData("path", msg.getBytes(), -1)
  }
}
