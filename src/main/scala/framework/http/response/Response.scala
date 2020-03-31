package framework.http.response

case class Response(data: String = "",
                    code: Int = 200,
                    _rsHeaders: Seq[(String, String)] = Seq()) {

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
  var _headers: Seq[(String, String)] = _rsHeaders

  // var _cookie: Cookie

  def status: Int = _status

  def status(status: Int): Response = {
    _status = status
    this
  }

  def headers: Seq[(String, String)] = _headers

  def headers(headers: Seq[(String, String)]): Response = {
    _headers = headers
    this
  }

  def addHeader(header: (String, String)): Response = {
    _headers = _headers :+ header
    this
  }

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
