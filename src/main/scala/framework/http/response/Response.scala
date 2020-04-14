package framework.http.response

case class Response(rsData: String = "",
                    rsCode: Int = 200,
                    rsHeaders: Seq[(String, String)] = Seq()) {

  val httpVersion = "HTTP/1.1"

  var _status: Int = 200
  var _headers: Seq[(String, String)] = rsHeaders

  // var _cookie: Cookie

  private var _data: String = rsData
  private var _code: Int = rsCode

  def code: Int = _code

  def code(code: Int): Response = {
    _code = code
    this
  }

  def data: String = _data

  def data(data: String): Response = {
    _data = data
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
