package framework.http

import app.config.Config
import framework.http.session.{AbstractSessionHandler, FileSessionHandler}

import scala.collection.mutable.ListBuffer

object SessionManager {

  def set(key: String, value: String): Unit = {
    val sessionHandler = getSessionHandler
    val ID = sessionHandler.init

    sessionHandler.setKeyByID(ID, key, value)
  }

  private def getSessionHandler: AbstractSessionHandler = {

    var sessionHandlersMap = ListBuffer[(String, AbstractSessionHandler)]()
    sessionHandlersMap = sessionHandlersMap :+ ("file", FileSessionHandler)

    val handler = sessionHandlersMap.filter(_._1 == Config.sessionType)
    if (handler.isEmpty) {
      throw new Exception(
        "No handler found for configured type: " + Config.sessionType
      )
    }

    handler.head._2
  }

  def get(key: String): Unit = {}

}
