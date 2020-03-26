package framework.http.request

import framework.http.response.Response
import framework.routing.RoutingManager

object RequestHandler {

  def handleRequest(request: Request): Response = {
    val route = RoutingManager.detectRoute(request)

    RoutingManager.resolveRoute(route, request)
  }
}
