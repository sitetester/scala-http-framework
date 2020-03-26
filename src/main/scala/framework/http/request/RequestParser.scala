package framework.http.request

import java.io.BufferedReader

object RequestParser {

  def ccToMap(cc: AnyRef): Map[String, Any] =
    (Map[String, Any]() /: cc.getClass.getDeclaredFields) { (a, f) =>
      f.setAccessible(true)
      a + (f.getName -> f.get(cc))
    }

  def parseRequest(in: BufferedReader, requestLines: Seq[String]): Request = {
    val request = new Request(in)

    parseRequestLine(requestLines.head, request)
    parseHeaders(requestLines, request)
    parseFormData(in, requestLines, request)
    MultiPartFormDataParser.parseMultiPartFormData(in, requestLines, request)

    request
  }

  private def parseRequestLine(requestLine: String,
                               request: Request): Request = {
    val requestLineParts = requestLine.split(" ")
    requestLineParts.foreach(println(_))

    request
      .method(requestLineParts.head)
      .uri(requestLineParts(1))
      .httpVersion(requestLineParts.last)
  }

  private def parseHeaders(requestLines: Seq[String],
                           request: Request): Request = {
    val headerLines = requestLines.drop(1).filter(line => line.length > 0)

    val headers = headerLines.map(headers => {
      val kv = headers.split(":")
      (kv.head, kv.tail.mkString)
    })

    request.headers(headers)
  }

  // https://stackoverflow.com/questions/52655265/file-upload-using-http-protocol-through-java-socket
  private def parseFormData(in: BufferedReader,
                            requestLines: Seq[String],
                            request: Request): Request = {

    val formData = ""
    request.formData(formData)
    request
  }

}

case class FormFieldData(name: String, value: String)

case class FormFileData(filename: String, contentType: String, contents: String)
