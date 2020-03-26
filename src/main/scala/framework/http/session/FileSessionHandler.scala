package framework.http.session

import java.io.{File, PrintWriter}
import java.util.UUID
import java.util.UUID.randomUUID

import app.config.Config

object FileSessionHandler extends AbstractSessionHandler {

  def init: UUID = {
    val ID = randomUUID()
    val path = Config.sessionDir + s"$ID.txt"

    try {
      val file = new File(path)
      file.createNewFile
    } catch {
      case e: Exception =>
        println(e.getMessage)
    }

    ID
  }

  def setKeyByID(ID: UUID, key: String, value: String): Unit = {
    val path = Config.sessionDir + s"$ID.txt"

    val pw = new PrintWriter(new File(path))
    pw.write(s"$key=$value")
    pw.close()
  }
}
