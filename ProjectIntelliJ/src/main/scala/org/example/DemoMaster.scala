package org.example

import akka.actor._
import akka.cluster.client.ClusterClientReceptionist
import com.typesafe.config.ConfigFactory


object DemoMaster {

  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.parseString("""
      akka {
        actor {
          provider = cluster
        }
        remote {
          netty.tcp {
            hostname = "127.0.0.1"
            port = 2551
          }
        }
        cluster {
          seed-nodes = ["akka.tcp://ClusterSystem@127.0.0.1:2551"]
        }
        extensions = ["akka.cluster.client.ClusterClientReceptionist"]
      }""")

    val system = ActorSystem("ClusterSystem", ConfigFactory.load(config))
    val master = system.actorOf(Props[ClusterMaster], "master")
    ClusterClientReceptionist(system).registerService(master)
  }

  class ClusterMaster extends Actor with ActorLogging {
    def receive: Receive = {
      case e =>
        log.info(s"from master : $e : $sender")
        sender ! s"OK ($e)"
    }
  }
}
