package framework.http.controller

import framework.http.request.Request
import framework.http.response.Response

object PublicController extends AbstractController {

  def index(request: Request): Response = {
    Response(request.uri)
  }
}
