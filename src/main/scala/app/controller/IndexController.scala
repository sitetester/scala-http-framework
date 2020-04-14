package app.controller

import framework.http.controller.AbstractController
import framework.http.request.Request
import framework.http.response.Response

object IndexController extends AbstractController {

  def index(request: Request): Response = {
    val fruits = Seq("Apple", "Orange", "Banana")
    Response(fruits.mkString(","))
  }

  def redirect(request: Request): Unit = {
    // redirect("http://www.google.com")
    // redirect("/session/save")
  }

  def postTest(request: Request): String = {
    /*request.formFields.foreach(kv => println(kv.head, kv.last))
    println(request.formField("param2"))*/

    ""
  }

  /*def renderTemplate(request: Request): Response = {
    Response(
      renderHTML("blog/slugLower")
    )
  }*/

  def renderTemplate(request: Request): Response = {
    render(
      "blog/messages",
      Map(
        "msg" -> "I like Scala :)",
        "tipOfDay" -> "Use debugging instead of printing ;)"
      )
    )
  }
}
