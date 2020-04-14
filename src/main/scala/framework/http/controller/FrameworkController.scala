package framework.http.controller

import framework.http.request.Request
import framework.http.response.Response

object FrameworkController extends AbstractController {

  def controllerNotFound(request: Request): Response = {
    Response("There is no controller to handle route: " + request.uri, 404)
  }

}
