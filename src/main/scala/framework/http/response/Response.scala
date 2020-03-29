package framework.http.response

case class Response(data: String = "",
                    code: Int = 200,
                    headers: Seq[(String, String)] = Seq()) {

  val httpVersion = "HTTP/1.1"

  val statusMap: Seq[(Int, String)] = Seq((200, "Success"))
  val STATUS_SUCCESS = 200
  val STATUS_CREATED = 201
  val STATUS_MOVED_PERMANENTLY = 301
  val STATUS_MOVED_TEMPORARILY = 302
  val STATUS_BAD_REQUEST = 400
  val STATUS_NOT_FOUND = 404
  val STATUS_INTERNAL_SERVER_ERROR = 500

  val contentTypeJson: String = "application/json"
  val contentTypeXml: String = "application/xml"

  var _status: Int = 200

  // var cookie: Cookie

  // val cookies
  // val cache
  var _headers: Seq[(String, String)] = Seq()

  def status: Int = _status

  def status(status: Int): Response = {
    _status = status
    this
  }

  /*def headers: Headers = Headers(_headers)
  def headers_=(headers: Seq[(String, String)]): Unit = {
    _headers = headers
  }*/

  def sendRedirect(location: String, code: Int = 301): Response = {
    val str =
      s"""
         |<head> 
         |  <meta http-equiv="Refresh" content="0; URL=$location">
         |</head>
         |""".stripMargin

    Response(str)
  }
}
