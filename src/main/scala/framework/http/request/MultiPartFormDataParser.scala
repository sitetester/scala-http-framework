package framework.http.request

import java.io.{
  BufferedReader,
  FileOutputStream,
  OutputStreamWriter,
  PrintWriter
}

import scala.collection.mutable.ArrayBuffer

object MultiPartFormDataParser {
  private val uploadsDir = "uploads/"

  def parseMultiPartFormData(in: BufferedReader,
                             requestLines: Seq[String],
                             request: Request): Request = {

    val formData = readContentLength(in, requestLines)

    /*println("formData ===")
    println(formData)
    println("formData ===")*/

    val hyphenPattern = "--------------------------([a-z0-9]+)--".r
    val boundaryOption = for (m <- hyphenPattern.findFirstMatchIn(formData))
      yield m.group(1)
    val boundary = boundaryOption.getOrElse("")

    var dataBuffer: ArrayBuffer[AnyRef] = ArrayBuffer()
    val lines = formData.split("\r\n")
    lines.zipWithIndex
      .foreach {
        case (line, index) =>
          if (line.startsWith("Content-Disposition") && line.contains(
                "form-data"
              )) {

            if (!line.contains("filename")) {
              val name = "name=\"(.+)\"".r.findFirstIn(line)
              dataBuffer += FormFieldData(
                name = name.getOrElse(""),
                value = lines(index + 2)
              )

            } else if (line.contains("filename")) {

              val filenamePattern = "filename=\"(.+)\"".r
              val filenameGroup =
                for (m <- filenamePattern.findFirstMatchIn(line))
                  yield m.group(1)
              val filename = filenameGroup.getOrElse("")

              val contentTypePattern = "Content-Type: (.+)".r
              val contentTypeGroup =
                for (m <- contentTypePattern.findFirstMatchIn(lines(index + 1)))
                  yield m.group(1)
              val contentType = contentTypeGroup.getOrElse("")

              dataBuffer += FormFileData(
                filename,
                contentType,
                contents = lines(index + 3)
              )

              val contents =
                getFileContents(lines, index + 3, boundary, filename)
              writeFile(filename, contents)
            }
          }
      }

    // println("\ndataBuffer---")
    // dataBuffer.foreach(println(_))

    request
  }

  private def getFileContents(lines: Array[String],
                              startingIndex: Int,
                              boundary: String,
                              filename: String): String = {
    var boundaryIndex = 0
    import scala.util.control.Breaks._
    breakable {
      lines
        .drop(startingIndex)
        .zipWithIndex
        .foreach {
          case (line, index) =>
            if (line.contains("--------------------------" + boundary)) {
              boundaryIndex = index
              break
            }
        }
    }

    println("startingIndex = " + startingIndex)
    println("boundaryIndex = " + boundaryIndex)

    val contents = for ((line, index) <- lines.zipWithIndex
                        if index >= startingIndex &&
                          index <= boundaryIndex + (startingIndex - 1))
      yield line

    contents.mkString("\n")
  }

  private def writeFile(filename: String, contents: String): Unit = {

    val os = new FileOutputStream(uploadsDir + filename)
    val pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))
    pw.write(contents)
    pw.close()
  }

  private def readContentLength(in: BufferedReader,
                                requestLines: Seq[String]): String = {
    val contentLength = getContentLength(requestLines)
    val charArray = new Array[Char](contentLength)
    in.read(charArray, 0, contentLength)

    new String(charArray)
  }

  private def getContentLength(requestLines: Seq[String]): Int = {
    var contentLength = 0

    val clHeader = requestLines.filter(_.startsWith("Content-Length"))
    if (clHeader.nonEmpty) {
      contentLength = clHeader.head.split(":").last.trim.toInt
    }

    contentLength
  }

}
