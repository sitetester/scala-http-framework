package framework.http.session

import java.util.UUID

trait AbstractSessionHandler {

  def init: UUID

  def setKeyByID(ID: UUID, key: String, value: String): Unit
}
