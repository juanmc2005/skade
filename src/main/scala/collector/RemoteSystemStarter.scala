package collector

import akka.actor.ActorSystem

/**
  * Created by juanma on 09/03/17.
  */
object RemoteSystemStarter extends App {

  val system = ActorSystem("RemoteAgentSystem")
}
