package collector

import java.lang.management.ManagementFactory

import akka.actor.ActorRef
import core.BaseAgent

/**
  * Created by juanma on 22/01/17.
  */
class CollectorAgent(system: ActorRef) extends BaseAgent(system) {

  var startedTimeMillis: Long = 0
  var startingContainer: String = ""
  var collectedData: List[TrackedData] = List()

  override def setup(): Unit = {
    startingContainer = here().path.name
    resetAndStartMoving()
  }

  override def afterMove(): Unit = {
    if (here().path.name == startingContainer) {
      printResults()
      resetAndStartMoving()
    } else if (here().path.name == "Container-1")
      gatherDataAndMoveTo("Container-2")
    else if (here().path.name == "Container-2")
      gatherDataAndMoveTo(startingContainer)
  }

  private def resetAndStartMoving() = {
    Thread.sleep(5000)
    startedTimeMillis = System.currentTimeMillis()
    collectedData = List()
    doMove("Container-1")
  }

  private def gatherDataAndMoveTo(destName: String) = {
    val os = ManagementFactory.getOperatingSystemMXBean
    collectedData = collectedData :+ new TrackedData(
      os.getSystemLoadAverage,
      Runtime.getRuntime.freeMemory,
      here().path.address.host.getOrElse(here().path.name))
    doMove(destName)
  }

  private def printResults() = {
    println(s"Tiempo: ${(System.currentTimeMillis() - startedTimeMillis) / 1000f} seg.")
    collectedData.foreach(data => {
      println()
      println(data.container)
      println("--------------------")
      println(s"Uso de CPU: ${data.averageSystemLoad}%")
      println(s"Memoria libre: ${data.freeMemory}")
      println()
    })
  }
}
