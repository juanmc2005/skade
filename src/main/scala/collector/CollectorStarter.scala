package collector

import akka.actor.{ActorSystem, Props}
import core.{AgentManager, NewContainer}

/**
  * Created by juanma on 30/01/17.
  */
object CollectorStarter extends App {

  val system = ActorSystem("AgentSystem")
  val agentManager = system.actorOf(Props[AgentManager], name = "AgentManager")

  agentManager ! NewContainer("Container-1")
  agentManager ! NewContainer("Container-2")
  agentManager ! new CollectorAgent(agentManager)

}
