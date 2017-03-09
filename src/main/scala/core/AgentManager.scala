package core

import akka.actor.{Actor, ActorRef, AddressFromURIString, Deploy, Props}
import akka.remote.RemoteScope

/**
  * Created by juanma on 22/01/17.
  */
class AgentManager extends Actor {

  private var containers = List(newContainer("Main-Container"))

  override def receive: Receive = {
    case a: Agent => containers.head ! StartingAgent(a)
    case NewContainer(n) => addContainer(newContainer(n))
    case NewRemoteContainer(n, p) => addContainer(newRemoteContainer(n, p))
    case GetContainer(n) => sender ! containerNamed(n)
  }

  private def newContainer(name: String): ActorRef =
    context.actorOf(Props[Container], name = name)

  private def newRemoteContainer(name: String, path: String): ActorRef =
    context.actorOf(
      Props[Container]
        .withDeploy(Deploy(scope = RemoteScope(AddressFromURIString(path)))),
      name = name
    )

  private def addContainer(ref: ActorRef): Unit =
    containers = containers :+ ref

  private def containerNamed(name: String): Option[ActorRef] =
    containers.find(c => c.path.name == name)
}
