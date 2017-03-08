package core

import akka.actor.ActorRef

/**
  * Created by juanma on 22/01/17.
  */
class BaseAgent(system: ActorRef) extends Agent(system) {

  override def setup(): Unit = {}

  override def beforeMove(): Unit = {}

  override def afterMove(): Unit = {}
}
