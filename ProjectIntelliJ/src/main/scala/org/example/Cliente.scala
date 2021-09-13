package org.example

import akka.actor.{ActorRef, ActorSystem, Props}
import org.example.Pingpong.{Actor1, system}


akka.extensions = ["akka.cluster.client.ClusterClientReceptionist"]

object ClusterClient extends App {

  class Client1 extends  {
    case class ClusterClientReceptionist(system: ActorSystem) {
      def registerService(serviceA: ActorRef): Unit = ???
    }

    case class ClusterClientSettings(system: ActorSystem) {
      def withInitialContacts(initialContacts: Any): Any = ???
    }

    def runOn(host1) {
      val service1 = system.actorOf(Props[Actor1], "service1")
      ClusterClientReceptionist(system).registerService(service1)
    }
    def runOn(client) {
      val service_c = system.actorOf(
        ClusterClient.props(ClusterClientSettings(system)), "client")
      service_c ! ClusterClient.Send("/user/serviceA", "hello", localAffinity = true)
    }

  }
}