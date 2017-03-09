package core

import akka.actor.{Actor, ActorRef, Props}

/**
  * Created by juanma on 22/01/17.
  */
class Container extends Actor {

  override def receive: Receive = {
    case newAgent: StartingAgent =>
      println(s"${self.path.name} received a new agent")
      newAgent.agent.location = Some(self)
      newAgent.agent.setup()
    case agent: Agent =>
      println(s"${self.path.name} received an agent")
      agent.location = Some(self)
      agent.afterMove()
  }

}
