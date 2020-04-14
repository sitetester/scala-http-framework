package framework.http.request

import framework.http.response.Response
import framework.routing.RoutingManager

class RequestHandler(routingManager: RoutingManager) {

  def handleRequest(request: Request): Response = {

    val route = routingManager.detectRoute(request)
    routingManager.resolveRoute(route, request)
  }
}
