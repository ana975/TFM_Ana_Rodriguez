package org.example

import akka.actor.{Actor, ActorSystem, Props}

object Pingpong extends App {

  class Actor1 extends Actor {
    def receive = {
      case "Ping" =>
        println("Pong")
        sender ! "Pong"
    }
  }
  val system = ActorSystem("System1")
  val actor = system.actorOf(Props[Actor1], "Actor1")
}
