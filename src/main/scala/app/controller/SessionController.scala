package app.controller

import framework.http.SessionManager
import framework.http.controller.AbstractController
import framework.http.request.Request
import framework.http.response.Response

object SessionController extends AbstractController {

  def save(request: Request): Response = {
    SessionManager.set("k111111111111", "v222222")

    Response("saved")
  }

  def get(): Response = {
    Response("blah getTED")
  }
}
