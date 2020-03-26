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

  def requestTest(request: Request): String = {
    // println(request.uri)
    // println(request.queryString)

    /*
    println(request.queryParamsCount)
    println(request.queryParam("p").mkString(","))
    println(request.queryParam("param1").mkString(","))

    request.queryParam("p").foreach(println(_))

    println("queryParamAsInt")
    request.queryParamAsInt("p").foreach(println(_))

    println("queryParamAsAlpha")
    request.queryParamAsAlpha("p").foreach(println(_))

    println("queryParamAsAlphaNum")
    request.queryParamAsAlphaNum("p").foreach(println(_))

    println("queryParamAsBool")
    request.queryParamAsBool("p").foreach(println(_))

    println("queryParamFilteredInt")
    request.queryParamFilteredInt("p").foreach(println(_))

    println(request.method,
            request.method == request.METHOD_GET,
            request.method == request.METHOD_POST)*/

    ""
  }

  // CAUTION!
  // reorder ScalaRouting routes if page not loading
  def headersTest(request: Request): String = {

    // println(request.headers)

    /*println(request.headers.getHeader("User-Agent"))
    println(request.headers.headerExists("User-Agent"))
    println(request.headers.headerExists("User-Agent"))*/

    println(request.headers.getHeader("Cookie"))
    request.cookies.foreach(p => {
      println(p.head, p.last)
    })

    ""
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

    renderViewWithVars(
      "blog/messages",
      Map(
        "msg" -> "I like Scala :)",
        "tipOfDay" -> "Use debugging instead of printing ;)"
      )
    )
  }
}
