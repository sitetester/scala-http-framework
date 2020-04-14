package test_app.controller

import framework.http.request.Request
import framework.http.response.Response
import framework.templating.TemplateManager

object IndexController {

  def index(request: Request): Response = {
    val fruits = Seq("Apple", "Orange", "Banana")
    Response(fruits.mkString(", "))
  }

  def renderTemplate(request: Request): Response = {
    TemplateManager.render(
      "src/test/resources/templating/views/blog/messages.html",
      Map("msg" -> "Hi!")
    )
  }
}
