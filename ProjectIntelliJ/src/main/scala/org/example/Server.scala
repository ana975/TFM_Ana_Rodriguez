package org.example

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}


object Server extends App {

  class Actor1 extends Actor with ActorLogging{
    def receive = {
      case "Ping" =>
        println("Pong")
        sender ! "Pong"
  }
  val system = ActorSystem("SystemServer")
  val server = system.actorOf(Props[Actor1], "Server")
  }
}


