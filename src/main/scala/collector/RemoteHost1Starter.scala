package collector

import akka.actor.{ActorSystem, Props}
import core.{AgentManager, NewContainer, NewRemoteContainer}

/**
  * Created by juanma on 09/03/17.
  */
object RemoteHost1Starter extends App {

  val system = ActorSystem("Host1AgentSystem")
  val agentManager = system.actorOf(Props[AgentManager], name = "AgentManager")

  agentManager ! NewContainer("Container-1")
  agentManager ! NewRemoteContainer("Container-2", Config.actorPath)
  agentManager ! new CollectorAgent(agentManager)
}
