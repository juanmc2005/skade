package collector

/**
  * Created by juanma on 22/01/17.
  */
class TrackedData(val averageSystemLoad: Double,
                  val freeMemory: Long,
                  val container: String) extends Serializable
