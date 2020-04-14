package framework.http.controller

import app.config.Config
import framework.http.response.Response
import framework.templating.TemplateManager

abstract class AbstractController {
  var fullPath = Config.appViewsPath

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

  def render(view: String, vars: Map[String, Any] = Map()): Response = {
    fullPath += view + ".html"
    TemplateManager.render(fullPath, vars)
  }

}
