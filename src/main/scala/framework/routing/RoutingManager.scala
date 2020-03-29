package framework.routing

import app.config.Config
import app.config.routing.ScalaRouting
import framework.http.request.Request
import framework.http.response.Response

import scala.util.control._

object RoutingManager {

  def detectRoute(request: Request): ScalaRoute = {

    val pathSegments = request.uri.split("/").drop(1)
    val routes = getConfigRoutes

    val outloop = new Breaks
    val inloop = new Breaks

    var route = new ScalaRoute()
    outloop.breakable {
      routes.foreach(scalaRoute => {
        val srPathSegments = scalaRoute.path.split("/").drop(1)

        if (pathSegments.length == srPathSegments.length) {
          var equal = true
          inloop.breakable {
            srPathSegments.zipWithIndex.foreach {
              case (path, index) =>
                // e.g blog
                if (!path.contains("{")) {
                  equal = path == pathSegments(index)
                } else if (path.contains("[")) {
                  val v = srPathSegments(index)
                  val start = v.indexOf("(")
                  val end = v.lastIndexOf(")")
                  var pattern = v.substring(start, end + 1)

                  pattern = pattern.drop(1).dropRight(1)
                  equal = pathSegments(index).matches(pattern)
                }

                if (!equal) {
                  inloop.break;
                }
            }
          }

          request.route(scalaRoute.path)

          if (equal) {
            route = scalaRoute
            outloop.break
          } else {
            route = new ScalaRoute()
              .path("public/errors/404")
              .controller("framework")
          }
        }
      })
    }

    route
  }

  private def getConfigRoutes: Seq[ScalaRoute] = {
    val format = Config.routingFormat

    var map: Map[String, RoutingFormat] = Map()
    map += ("scala" -> ScalaRouting)

    val rf = map.filter(_._1 == format).head._2
    rf.asInstanceOf[RoutingFormat].getRoutes
  }

  def resolveRoute(route: ScalaRoute, request: Request): Response = {
    var response: Response = Response()

    if (route.controller.contains("framework")) {
      val filename = Config.appPublicPath + "error/404.html"
      val source = io.Source.fromFile(filename)
      val view = source.getLines.mkString
      source.close()

      return Response(view)
    }

    val controllerParts = route.controller.split("::")
    val controller = controllerParts.head
    val method = controllerParts.last

    val args: Array[String] = Array()

    /*if (route.path.contains("/auth/basic")) {
      return BasicAuthentication.authenticate()
    }*/

    /*if (route.path.contains("{")) {
      args = route.path
        .split("/")
        .filter(_.trim != "")
        .filter(arg => arg.startsWith("{"))
    }*/

    /*val params = request.uri
      .split("/")
      .takeRight(args.length)
      .map(arg => {
        try {
          arg.toInt
        } catch {
          case _: NumberFormatException => arg
        }
      })*/

    val o = Class.forName(controller)
    if (args.length > 0) {
      // val m = o.getDeclaredMethod(method, classOf[Request], classOf[Int])
      val m = o.getDeclaredMethod(method, classOf[Request])
      // response = m.invoke(o, request, request.uri.split("/").takeRight(args.length).head.toInt).toString
      response = m.invoke(o, request).asInstanceOf[Response]

    } else {
      val m = o.getDeclaredMethod(method, classOf[Request])
      response = m.invoke(o, request).asInstanceOf[Response]
    }

    response
  }
}
