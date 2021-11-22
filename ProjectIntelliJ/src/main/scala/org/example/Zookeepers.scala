package org.example

import akka.stream.impl.Stages.DefaultAttributes.watch
import com.sun.javafx.robot.impl.FXRobotHelper.getChildren
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.zookeeper.KeeperException
import org.example.SparkSQL.execute
import org.example.Zookeepers.zk

import scala.List
import scala.concurrent.Await
import scala.language.postfixOps
import scala.reflect.internal.util.NoSourceFile.path



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
        zk.setData("/tables/" + pathname, msg.getBytes(), -1)
      }
      val drop = msg.startsWith("DROP TABLE")
      if (drop) {
        val name = msg.substring(11)
        zk.delete("/tables/"+ name, -1)
      }
    }

    public zk.getChildren("/tables",false)
      throw KeeperException; InterruptedException; {
        return zk.getChildren(path, watch ? watchManager.defaultWatcher: Null)
      }
    def readzookeeper(nodelist:String): Unit = {
    var info = ""
      for (node<- nodelist){
        info = info + zk.getData("/tables/" + node, false, null).toString()
        execute(info)
      }
    }
}
