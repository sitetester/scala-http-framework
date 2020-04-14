package framework.http.request

import java.io.BufferedReader

import framework.http.Headers

// https://developer.mozilla.org/en-US/docs/Web/HTTP
class Request(in: BufferedReader) {

  val METHOD_GET = "GET"
  val METHOD_POST = "POST"
  val METHOD_PUT = "PUT"
  val METHOD_DELETE = "DELETE"

  // host
  private var _remoteHost = ""
  private var _remoteAddress = ""
  private var _remoteUser = ""

  // uri
  private var _uri = ""
  private var _route = ""
  private var _uriParams = Seq[(String, String)]()

  // http
  private var _isHttps: Boolean = false
  private var _httpVersion = "HTTP/1.1"
  private var _method = ""
  private var _headers: Seq[(String, String)] = Seq()

  // TODO
  private var _files = ""
  private var _formData = ""
  private var _postParams = ""

  // def bufferedReader: BufferedReader = in

  def remoteHost: String = _remoteHost

  def remoteHost(remoteHost: String): Request = {
    _remoteHost = remoteHost
    this
  }

  def remoteAddress: String = _remoteAddress

  def remoteAddress(remoteAddress: String): Request = {
    _remoteAddress = remoteAddress
    this
  }

  def method: String = _method
  def method(method: String): Request = {
    _method = method
    this
  }

  def uri: String = _uri
  def uri(uri: String): Request = {
    _uri = uri
    this
  }

  def route: String = _route
  def route(route: String): Request = {
    _route = route
    this
  }

  def uriParam(name: String): String = {
    uriParams.foreach(tuple => {
      val param = tuple._1

      if (!param.contains("\\[")) {
        return tuple._2
      } else {
        val i = param.indexOf("(")
        return param.substring(0, i)
      }
    })

    ""
  }

  def uriParams: Seq[(String, String)] = {
    var params: Seq[(String, String)] = Seq()

    var index = -1
    _route.split("/").drop(1).foreach {
      case (path) => {
        index += 1
        if (path.contains("{")) {
          val param = path.drop(1).dropRight(1)
          val _uriParams = _uri.split("/").drop(1)
          params = params :+ (param, _uriParams(index))
        }
      }
    }

    params
  }

  def uriParamsWithoutRegex: Seq[(String, String)] = {
    var params: Seq[(String, String)] = Seq()

    var index = -1
    _route.split("/").drop(1).foreach {
      case (path) => {
        index += 1
        if (path.contains("{")) {
          var param = path.drop(1).dropRight(1)
          param = param.substring(0, param.indexOf("("))
          val _uriParams = _uri.split("/").drop(1)
          params = params :+ (param, _uriParams(index))
        }
      }
    }

    params
  }

  def queryParamAsInt(param: String): Array[Int] = {
    queryParam(param).map(v => {
      try {
        v.toInt
      } catch {
        case _: Exception => 0
      }
    })
  }

  def queryParamAsAlpha(param: String): Array[String] = {
    queryParam(param).map(v => {
      v.replaceAll("[^a-zA-Z]", "")
    })
  }

  def queryParamAsAlphaNum(param: String): Array[String] = {
    queryParam(param).map(v => {
      v.replaceAll("[^0-9a-zA-Z]", "")
    })
  }

  def queryParamAsBool(param: String): Array[Boolean] = {
    queryParam(param).map(v => {
      try {
        v.toBoolean
      } catch {
        case _: Exception => false
      }
    })
  }

  def queryParam(param: String): Array[String] = {
    queryString
      .split("&")
      .filter(_.split("=").head.equals(param))
      .map(kv => kv.split("=").last)
  }

  def queryString: String = {
    _uri.split("\\?").drop(1).mkString
  }

  def queryParamFilteredInt(param: String): Array[Int] = {
    queryParam(param)
      .map(v => {
        v.replaceAll("[^0-9]", "")
      })
      .filter(s => s != "")
      .map(_.toInt)
  }

  def queryParamsCount: Int = {
    queryParams.length
  }

  def queryParams: Array[String] = {
    queryString.split("&").map(_.split("=").head).distinct
  }

  def httpVersion: String = _httpVersion

  def httpVersion(httpVersion: String): Request = {
    _httpVersion = httpVersion
    this
  }

  def headers(headers: Seq[(String, String)]): Request = {
    _headers = headers
    this
  }

  def isHttps: Boolean = _isHttps
  def isHttps(isHttps: Boolean): Request = {
    _isHttps = isHttps
    this
  }

  def remoteUser: String = _remoteUser
  def remoteUser(remoteUser: String): Request = {
    _remoteUser = remoteUser
    this
  }

  def formData(formData: String): Request = {
    _formData = formData
    this
  }

  def formField(name: String): String = {
    formFields.filter(_.head.equals(name)).head.last
  }

  def formFields: Array[Array[String]] = {
    formData.split("&").map(kv => kv.split("="))
  }

  def formData: String = _formData

  def getCookie(name: String): String = {
    cookies.filter(_._1 == name).head._2
  }

  // Example: https://php.uz/manual/en/function.http-parse-cookie.php
  def cookies: Array[(String, String)] = {
    headers
      .getHeader("Cookie")
      .split(";")
      .map(c => {
        val s = c.split("=")
        (s.head, s.tail.head)
      })
  }

  def headers: Headers = Headers(_headers)

}
