import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.net.ServerSocket
import java.text.SimpleDateFormat
import java.util.Date

import framework.http.request.{RequestHandler, RequestParserReadLine}
import framework.http.response.Response

import scala.collection.mutable.ArrayBuffer

object ServerReadingViaInDotReadLine extends App {
  val port = 8081

  try {
    val serverConnect = new ServerSocket(port)
    println(s"Server started at port $port")

    while (true) {
      val clientSocket = serverConnect.accept

      val thread = new Thread {

        override def run(): Unit = {
          val time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
          println("\nNew connection opened at: " + time.format(new Date()))

          val out = new PrintWriter(clientSocket.getOutputStream, true)
          val in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream)
          )
          var msgBuffer: ArrayBuffer[String] = ArrayBuffer()

          var readLine = in.readLine()
          while (readLine != null && readLine != "") {
            msgBuffer += readLine
            readLine = in.readLine()
          }

          val request = RequestParserReadLine.parseRequest(in, msgBuffer.toSeq)
          val response = RequestHandler.handleRequest(request)

          send(in, out, response)
        }
      }

      thread.start()
    }

  } catch {
    case e: Exception =>
      println("Exception: " + e.getMessage + "\ngetCause:\t" + e.getCause)
  }

  def send(in: BufferedReader, out: PrintWriter, response: Response): Unit = {
    // out.println(s"""HTTP/1.1 401 Unauthorized""")
    // out.println("WWW-Authenticate: Basic realm=\"Our Site\"")

    // out.println("")
    // out.println(response.data)
    // in.close()
  }
}
