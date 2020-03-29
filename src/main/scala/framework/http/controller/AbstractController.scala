package framework.http.controller

import app.config.Config
import framework.http.response.Response
import framework.templating.TemplateManager

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
    val fullPath = Config.appViewsPath + view + ".html"
    val source = Source.fromFile(fullPath)
    val str = source.getLines.mkString
    source.close()
    str
  }

  def render(view: String, vars: Map[String, Any] = Map()): Response = {
    TemplateManager.render(view, vars)
  }

}
