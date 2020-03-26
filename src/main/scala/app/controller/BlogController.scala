package app.controller

import framework.http.request.Request
import framework.http.response.Response

object BlogController {

  def index(): String = {
    "<h1>Blog page</h1>"
  }

  def slugLower(request: Request): Response = {

    Response(
      s"<h1>e.g Blog ***slugLower*** page</h1>" +
        s"uri = ${request.uri}<br/>"
    )
  }

  def slugUpper(request: Request): Response = {
    Response(
      s"<h1>e.g Blog ***slugUpper*** page</h1>" +
        s"uri = ${request.uri}<br/>"
    )
  }

  def idRoute(request: Request): Response = {
    Response(
      s"<h1>e.g Blog page # x</h1>" +
        s"uri = ${request.uri}<br/>" +
        s"route = ${request.route}<br/>" +
        s"uriParams = ${request.uriParams}<br/>"
    )
  }

  def idRoute100(request: Request): Response = {
    Response(
      s"<h1>e.g Blog 100 page</h1>" +
        s"uri = ${request.uri}<br/>"
    )
  }

  def idRoute12(request: Request): Response = {
    Response(
      s"<h1>e.g Blog page # x</h1>" +
        s"uri = ${request.uri}<br/>" +
        s"route = ${request.route}<br/>" +
        s"uriParams = ${request.uriParams}<br/>" +
        s"uriParam(id1) = ${request.uriParam("id1")}<br/>" +
        s"uriParam(id2) = ${request.uriParam("id2")}<br/>"
    )
  }

}
