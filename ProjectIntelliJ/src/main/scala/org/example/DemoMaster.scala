package org.example


import akka.actor._
import akka.cluster.client.ClusterClientReceptionist
import com.typesafe.config.ConfigFactory
import org.example.SparkSQL.execute

import scala.util.{Failure, Success, Try}


object DemoMaster {

  case class Query(msg: String)

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
        extensions = ["akka.stdout-loglevel = "INFO""]
      }""")

    val system = ActorSystem("ClusterSystem", ConfigFactory.load(config))
    val master = system.actorOf(Props[ClusterMaster], "master")
    ClusterClientReceptionist(system).registerService(master)

  }

  class ClusterMaster extends Actor with ActorLogging {
    def receive: Receive = {
      case Query(msg) =>
        val e = execute(msg)
        Try(e) match {
          case Success(i) =>
            sender() ! println(s"Response: OK")
          case Failure(i) =>
            sender() ! println(s"Response: Fail")
        }
    }
  }
}
