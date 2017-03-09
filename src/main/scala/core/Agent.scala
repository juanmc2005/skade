package core

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by juanma on 22/01/17.
  */
abstract case class Agent(system: ActorRef) {

  var location: Option[ActorRef] = None

  def setup(): Unit
  def beforeMove(): Unit
  def afterMove(): Unit

  final def doMove(dest: String): Unit = {
    implicit val timeout = Timeout(5 seconds)
    beforeMove()
    system.ask(GetContainer(dest))
      .mapTo[Option[ActorRef]]
      .onComplete {
        case Success(maybeRef) =>
          if (maybeRef.isDefined) maybeRef.get ! this
          else println(s"No such Container: $dest")
        case Failure(e) => e.printStackTrace()
      }
  }

  def here(): ActorRef = location.get
}

case class NewContainer(name: String)
case class NewRemoteContainer(name: String, path: String)
case class StartingAgent(agent: Agent)
case class GetContainer(name: String)