package org.example

import akka.actor.{Actor, ActorLogging, ActorPath, ActorSystem, Props}
import akka.cluster.client.{ClusterClient, ClusterClientSettings}
import com.typesafe.config.ConfigFactory
import scala.io.StdIn

object DemoClient {

  def main(args : Array[String]) {
    val config = ConfigFactory.parseString("""
     akka {
       log-dead-letters = OFF
       actor {
         provider = "akka.remote.RemoteActorRefProvider"
       }

       remote {
         transport = "akka.remote.netty.NettyRemoteTransport"
         log-remote-lifecycle-events = off
         netty.tcp {
          hostname = "localhost"
          port = 5000
         }
       }
     }""")

    val system = ActorSystem("OUTSIDER-SYSTEM", ConfigFactory.load(config))
    val initialContacts = Set(
      ActorPath.fromString("akka.tcp://ClusterSystem@localhost:2551/system/receptionist"))
    val cc = system.actorOf(ClusterClient.props(
      ClusterClientSettings(system).withInitialContacts(initialContacts)), "os-client")
    val ccActor = system.actorOf(Props[ClusterClientActor], "ccActor")

    cc ! ClusterClient.Send("/user/master", ccActor, localAffinity = true)
    var line = ""
    while ({line = StdIn.readLine (); line != null}) {println (line)}
     cc ! ClusterClient.Send("/user/master", line, localAffinity = true)
  }

  class ClusterClientActor extends Actor with ActorLogging {
    def receive = {
      case e =>
        log.info(s"from cluster-client : $e : $sender")
        println (e)
    }
  }
}

