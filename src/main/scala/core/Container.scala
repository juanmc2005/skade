package core

import akka.actor.{Actor, ActorRef}

/**
  * Created by juanma on 22/01/17.
  */
class Container(system: ActorRef) extends Actor {

  override def receive: Receive = {
    case newAgent: StartingAgent =>
      newAgent.agent.currentRef = Some(self)
      newAgent.agent.setup()
    case agent: Agent =>
      agent.currentRef = Some(self)
      agent.afterMove()
  }

}
