package framework.routing

trait RoutingFormat {

  def getRoutes: Seq[ScalaRoute]
}
