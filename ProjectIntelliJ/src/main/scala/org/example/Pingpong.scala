package org.example

import akka.actor.{Actor, ActorSystem, Props}

/**
 * Hello world!
 *
 */
object Pingpong extends App {

  val system = ActorSystem("SimpleSystem")
  val actor = system.actorOf(Props[SimpleActor], "SimpleActor")

  actor ! "Hello"

  system.terminate()

  class SimpleActor extends Actor {
    def receive = {
      case s: String => println("s: " + s)
    }
  }
}
