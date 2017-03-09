package collector

import com.typesafe.config.ConfigFactory

/**
  * Created by juanma on 09/03/17.
  */
object Config {

  private val config = ConfigFactory.load()

  lazy val actorPath: String = config.getString("remote-deployment.path")
}
