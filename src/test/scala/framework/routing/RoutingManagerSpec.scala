package framework.routing

import java.io.{BufferedReader, InputStreamReader}

import framework.http.request.{Request, RequestHandler, RequestParser}
import framework.http.response.Response
import org.scalatest.flatspec.AnyFlatSpec

class RoutingManagerSpec extends AnyFlatSpec {

  it should "load Request with / path" in {
    val requestLines: Seq[String] =
      Seq("GET / HTTP/1.1", "Host: localhost:8081", "User-Agent: curl/7.64.1")

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])

    var routes: Seq[ScalaRoute] = Seq()
    routes = routes :+ new ScalaRoute()
      .path("/")
      .controller("test_app.controller.IndexController::index")

    val requestHandler = new RequestHandler(new RoutingManager(routes))

    val response = requestHandler.handleRequest(request)
    assert(response.isInstanceOf[Response])

    assert(response.data == "Apple, Orange, Banana")
  }

  it should "load Request with INT params" in {
    val requestLines: Seq[String] = Seq(
      "GET /blog/123 HTTP/1.1",
      "Host: localhost:8081",
      "User-Agent: curl/7.64.1"
    )

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])

    var routes: Seq[ScalaRoute] = Seq()

    routes = routes :+ new ScalaRoute()
      .path("/blog/{id([0-9]+)}")
      .controller("test_app.controller.BlogController::idRoute")

    val requestHandler = new RequestHandler(new RoutingManager(routes))

    val response = requestHandler.handleRequest(request)
    assert(response.isInstanceOf[Response])
    assert(response.data == "OK")
  }

  it should "load Request for /blog/{slug([A-Z]+)} route" in {
    val requestLines: Seq[String] =
      Seq(
        "GET /blog/SOME_UPPER_CASE_WORD  HTTP/1.1",
        "Host: localhost:8081",
        "User-Agent: curl/7.64.1"
      )

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])

    var routes: Seq[ScalaRoute] = Seq()
    routes = routes :+ new ScalaRoute()
      .path("/blog/{slug([_A-Z]+)}")
      .controller("test_app.controller.BlogController::slugUpper")

    val requestHandler = new RequestHandler(new RoutingManager(routes))

    val response = requestHandler.handleRequest(request)
    assert(response.isInstanceOf[Response])

    assert(response.data == "Blog ***slugUpper*** page")
  }

  it should "load Request for /blog/{slug([_a-z]+)} route" in {
    val requestLines: Seq[String] =
      Seq(
        "GET /blog/some_lower_case_word  HTTP/1.1",
        "Host: localhost:8081",
        "User-Agent: curl/7.64.1"
      )

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])

    var routes: Seq[ScalaRoute] = Seq()
    routes = routes :+ new ScalaRoute()
      .path("/blog/{slug([_a-z]+)}")
      .controller("test_app.controller.BlogController::slugLower")

    val requestHandler = new RequestHandler(new RoutingManager(routes))

    val response = requestHandler.handleRequest(request)
    assert(response.isInstanceOf[Response])

    assert(response.data == "Blog ***slugLower*** page")
  }

  it should "load Request for /blog/{id1}/{id2} route" in {
    val requestLines: Seq[String] =
      Seq(
        "GET /blog/123/456  HTTP/1.1",
        "Host: localhost:8081",
        "User-Agent: curl/7.64.1"
      )

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])

    var routes: Seq[ScalaRoute] = Seq()
    routes = routes :+ new ScalaRoute()
      .path("/blog/{id1}/{id2}")
      .controller("test_app.controller.BlogController::idRoute12")

    val requestHandler = new RequestHandler(new RoutingManager(routes))

    val response = requestHandler.handleRequest(request)
    assert(response.isInstanceOf[Response])

    assert(response.data == "OK")
  }

  it should "render template" in {
    val requestLines: Seq[String] =
      Seq(
        "GET /renderTemplate  HTTP/1.1",
        "Host: localhost:8081",
        "User-Agent: curl/7.64.1"
      )

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])

    var routes: Seq[ScalaRoute] = Seq()
    routes = routes :+ new ScalaRoute()
      .path("/renderTemplate")
      .controller("test_app.controller.IndexController::renderTemplate")

    val requestHandler = new RequestHandler(new RoutingManager(routes))

    val response = requestHandler.handleRequest(request)
    assert(response.isInstanceOf[Response])

    assert(response.data.contains("Message says: Hi!"))
  }

  it should "show 404 code" in {
    val requestLines: Seq[String] =
      Seq(
        "GET /some_bogus_route  HTTP/1.1",
        "Host: localhost:8081",
        "User-Agent: curl/7.64.1"
      )

    val in = new BufferedReader(new InputStreamReader(() => ???))
    val request = RequestParser.parseRequest(in, requestLines)

    assert(request.isInstanceOf[Request])

    val routes: Seq[ScalaRoute] = Seq()
    val requestHandler = new RequestHandler(new RoutingManager(routes))

    val response = requestHandler.handleRequest(request)
    assert(response.isInstanceOf[Response])

    assert(response.code == 404)
  }

}
