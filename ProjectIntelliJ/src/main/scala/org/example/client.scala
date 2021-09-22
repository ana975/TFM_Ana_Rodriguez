package org.example

import akka.actor.{Actor, ActorLogging}
import akka.cluster.client.{ClusterClient, ClusterClientSettings}


object client extends App {
  class Sender1 extends Actor with ActorLogging {
      val system = system.actorOf(ClusterClient.props(ClusterClientSettings(system).withInitialContacts(initialContacts)),
        "SystemClient")
      val initialContacts = Set(system.actorSelection("akka.tcp: //ClusterSystem@127.0.0.1: 2551/user/Server"))
      val c = system.actorOf(ClusterClient.props(initialContacts), "Client")
      c ! ClusterClient.Send("/ user / Server", "Ping", localAffinity = true)
    }
  }
}

