package core

import akka.actor.{Actor, ActorRef, Props}

/**
  * Created by juanma on 22/01/17.
  */
class AgentManager extends Actor {

  private var containers = List(newContainer("MainContainer"))

  override def receive: Receive = {
    case a: Agent => containers.head ! StartingAgent(a)
    case NewContainer(n) => addContainer(n)
    case GetContainer(n) => sender ! containerNamed(n)
  }

  private def newContainer(name: String): ActorRef =
    context.actorOf(Props(new Container(self)), name = name)

  private def addContainer(name: String): Unit =
    containers = containers :+ newContainer(name)

  private def containerNamed(name: String): Option[ActorRef] =
    containers.find(c => c.path.name == name)
}
