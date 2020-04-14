package framework.http.request

import java.io.{BufferedReader, InputStreamReader}

import app.config.routing.RoutingLoader
import framework.http.Headers
import framework.routing.RoutingManager
import org.scalatest.flatspec.AnyFlatSpec

class RequestParserSpec extends AnyFlatSpec {

  it should "load Request" in {
    val requestLines: Seq[String] =
      Seq("GET / HTTP/1.1", "Host: localhost:8081", "User-Agent: curl/7.64.1")

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])
    assert(request.method == request.METHOD_GET)
    assert(request.httpVersion == "HTTP/1.1")
    assert(request.uri == "/")
    assert(!request.isHttps)

    assert(request.headers.getHeader("Host") == "localhost:8081")
    assert(request.headers.getHeader("User-Agent") == "curl/7.64.1")

    assert(request.headers.isInstanceOf[Headers])

    val routingManager = new RoutingManager(RoutingLoader.getConfigRoutes)
    val response = new RequestHandler(routingManager).handleRequest(request)

    assert(request.route == "/")
  }

  it should "load Request with query params" in {
    val requestLines: Seq[String] = Seq(
      "GET /?page=1&page=2&active=true&locked=false&lang=en&msg=123abc HTTP/1.1",
      "Host: localhost:8081",
      "User-Agent: curl/7.64.1"
    )

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])

    assert(request.queryParamAsInt("page") sameElements Array(1, 2))
    assert(request.queryParamAsInt("page").head == 1)

    assert(request.queryParamAsBool("active").head)
    assert(!request.queryParamAsBool("locked").head)

    assert(request.queryParamAsAlpha("msg").head == "abc")
    assert(request.queryParam("msg").head == "123abc")

    assert(
      request.queryString == "page=1&page=2&active=true&locked=false&lang=en&msg=123abc"
    )

    assert(request.queryParamsCount == 5)
  }

  it should "load Request with POST params" in {
    val requestLines: Seq[String] =
      Seq(
        "POST /formFields HTTP/1.1",
        "Host: localhost:8081",
        "Content-Type: application/x-www-form-urlencoded",
        "Content-Length: 27",
        "User-Agent: curl/7.64.1",
        "",
        "field1=value1|&field2=value2"
      )

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])
    println(request.formFields)
  }

  it should "load Request with cookies" in {
    val requestLines: Seq[String] = Seq(
      "GET /?page=1 HTTP/1.1",
      "Host: localhost:8081",
      "User-Agent: curl/7.64.1",
      "Cookie:SID=123def;tracking_ID=456"
    )

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])

    assert(request.cookies.length == 2)
    assert(request.getCookie("SID") == "123def")
    assert(request.getCookie("tracking_ID") == "456")

  }
}
