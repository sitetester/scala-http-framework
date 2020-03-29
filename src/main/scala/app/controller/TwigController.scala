package app.controller

import framework.http.controller.AbstractController
import framework.http.request.Request
import framework.http.response.Response

case class User(name: String, pw: String)

object TwigController extends AbstractController {

  def index(request: Request): Response = {
    val u = User("john", "demo")

    render("twig/index", Map("request" -> request))
  }

}
