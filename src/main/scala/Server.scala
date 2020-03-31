import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.net.ServerSocket
import java.text.SimpleDateFormat
import java.util.Date

import framework.http.request.{RequestHandler, RequestParserReadLine}
import framework.http.response.Response

import scala.collection.mutable.ArrayBuffer

object Server extends App {

  val port = 8081

  try {
    val serverSocket = new ServerSocket(port)
    println(s"Server started at port $port")

    while (true) {
      val clientSocket = serverSocket.accept

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

          response.addHeader("cache-control", "no-cache")

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
    out.println(s"""HTTP/1.1 ${response.status} Success""")

    response.headers.foreach(h => {
      out.println(s"""${h._1}: ${h._2}""")
    })

    out.println("")
    out.println(response.data)
    in.close()
  }
}
