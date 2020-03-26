package framework.http

case class Headers(headers: Seq[(String, String)] = Seq()) {

  def getHeader(header: String): String = {
    if (header.length > 0) {
      headers.filter(_._1 == header).head._2
    } else {
      "None"
    }
  }

  def headerExists(header: String): Boolean = {
    headers.count(_._1 == header) > 0
  }

  def acceptableContentTypes: Array[String] = {
    // TODO
    Array("")
  }

  def languages: Array[String] = {
    // TODO
    Array("")
  }

  def charsets: Array[String] = {
    // TODO
    Array("")
  }

  def encodings: Array[String] = {
    // TODO
    Array("")
  }

}
