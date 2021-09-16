package org.example

import akka.actor.{ActorPath, Props}
import akka.cluster.client.{ClusterClient, ClusterClientReceptionist, ClusterClientSettings}
import org.example.Pingpong.system
import org.example.Pingpong.Actor1


akka.extensions = ["akka.cluster.client.ClusterClientReceptionist"]

object client extends App {

  def runOn(client: client.type) =

  runOn(Actor1) {
    val serviceA = system.actorOf(Props[Actor1], "serviceA")
    ClusterClientReceptionist(system).registerService(serviceA)
  }
  runOn(client) {
    val c = system.actorOf(
      ClusterClient.props(ClusterClientSettings(system).withInitialContacts(initialContacts)),
        "client")
    c ! ClusterClient.Send("/Pingpong", localAffinity = true)
    }
  val initialContacts = Set(ActorPath.fromString("akka.tcp://System1@127.0.0.1:2551"))
  val settings = ClusterClientSettings(system).withInitialContacts(initialContacts)

}



