package org.example

import org.apache.zookeeper.{Watcher, ZooKeeper}

object Zookeepers {

  abstract class executor extends Watcher {
    var filename = ""
    def this(filename: String) {
      this()
      this.filename = filename
    }

    var i = 0
    def zookeeper(msg: String): Unit = {
      val result = msg.startsWith("CREATE TABLE")
      if (result == true) {
        i = 1+i
        val zk = new ZooKeeper("127.0.0.1:2181", 30000, this)
        zk.setData("tables/test" + i, msg.getBytes(), -1)
      }
    }
  }
}
