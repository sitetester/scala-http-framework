package app.config.routing

import app.config.Config
import framework.routing.{RoutingFormat, ScalaRoute}

object RoutingLoader {

  def getConfigRoutes: Seq[ScalaRoute] = {
    val format = Config.routingFormat

    var map: Map[String, RoutingFormat] = Map()
    map += ("scala" -> ScalaRouting)

    val rf = map.filter(_._1 == format).head._2
    rf.asInstanceOf[RoutingFormat].getRoutes
  }
}
