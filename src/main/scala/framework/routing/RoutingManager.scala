package framework.routing

import framework.http.request.Request
import framework.http.response.Response

import scala.util.control._

class RoutingManager(routes: Seq[ScalaRoute]) {

  def detectRoute(request: Request): ScalaRoute = {

    val pathSegments = request.uri.split("/").drop(1)

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

  def resolveRoute(route: ScalaRoute, request: Request): Response = {
    var response: Response = Response()

    val controllerParts = route.controller.split("::")
    val controller = controllerParts.head
    val method = controllerParts.last

    val args: Array[String] = Array()

    try {
      val o = Class.forName(controller)
    } catch {
      case e: ClassNotFoundException =>
        System.out.println(e.getMessage)

        val o = Class.forName("framework.http.controller.FrameworkController")
        val m = o.getDeclaredMethod("controllerNotFound", classOf[Request])
        response = m.invoke(o, request).asInstanceOf[Response]
        return response
    }

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
