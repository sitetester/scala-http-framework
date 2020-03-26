package framework.http.controller

import framework.http.response.Response

import scala.io.Source

abstract class AbstractController {

  private val response: Response = Response()

  /**
    * Works for both external and internal path
    * Examples:
    * External: http://www.google.com
    * Internal: /session/save
    */
  def redirect(location: String): Response = {
    response.sendRedirect(location)
  }

  def renderHTML(view: String): String = {
    val fullPath = "src/main/scala/app/views/" + view + ".html"
    val source = Source.fromFile(fullPath)
    val str = source.getLines.mkString
    source.close()
    str
  }

  def renderViewWithVars(view: String, vars: Map[String, String]): Response = {
    val fullPath = "src/main/scala/app/views/" + view + ".html"

    val source = Source.fromFile(fullPath)
    var tpl = source.getLines.mkString
    source.close()

    vars.keys.foreach(key => {
      tpl = tpl.replaceAll("\\{" + key + "\\}", vars(key))
    })

    var h: Seq[(String, String)] = Seq(
      ("Content-Type", "text/html; charset=UTF-8")
    )
    Response(tpl, 200, h)
  }
}
