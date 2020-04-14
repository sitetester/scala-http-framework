package test_app.controller

import framework.http.request.Request
import framework.http.response.Response

object BlogController {

  def idRoute(request: Request): Response = {
    Response("OK")
  }

  def slugUpper(request: Request): Response = {
    Response(s"Blog ***slugUpper*** page")
  }

  def slugLower(request: Request): Response = {
    Response(s"Blog ***slugLower*** page")
  }

  def idRoute12(request: Request): Response = {
    Response("OK")
  }
}
