package app.config.routing

import framework.routing.{RoutingFormat, ScalaRoute}

object ScalaRouting extends RoutingFormat {

  def getRoutes: Seq[ScalaRoute] = {
    var routes: Seq[ScalaRoute] = Seq()

    routes = routes :+ new ScalaRoute()
      .path("/public")
      .controller("framework.http.controller.PublicController::index")

    routes = routes :+ new ScalaRoute()
      .path("/twig")
      .controller("app.controller.TwigController::index")

    routes = routes :+ new ScalaRoute()
      .path("/")
      .controller("app.controller.IndexController::index")

    routes = routes :+ new ScalaRoute()
      .path("/blog/{slug([A-Z]+)}")
      .controller("app.controller.BlogController::slugUpper")

    routes = routes :+ new ScalaRoute()
      .path("/blog/{slug([a-z]+)}")
      .controller("app.controller.BlogController::slugLower")

    routes = routes :+ new ScalaRoute()
      .path("/blog/{id([0-9])}")
      .controller("app.controller.BlogController::idRoute")

    routes = routes :+ new ScalaRoute()
      .path("/blog/{id(\\d+)}")
      .controller("app.controller.BlogController::idRoute100")

    routes = routes :+ new ScalaRoute()
      .path("/blog/{id1}/{id2}") // put regex to appy restrictions and make it narrow/explicit
      .controller("app.controller.BlogController::idRoute12")

    routes = routes :+ new ScalaRoute()
      .path("/session/save")
      .controller("app.controller.SessionController::save")

    routes = routes :+ new ScalaRoute()
      .path("/redirect")
      .controller("app.controller.IndexController::redirect")

    routes = routes :+ new ScalaRoute()
      .path("/renderTemplate")
      .controller("app.controller.IndexController::renderTemplate")

    /*
    routes = routes :+ new ScalaRoute()
      .path("/session/get")
      .controller("app.controller.SessionController::get")

    routes = routes :+ new ScalaRoute()
      .path("/auth/basic")

    routes = routes :+ new ScalaRoute()
      .path("/postTest")
      .controller("app.controller.IndexController::postTest")

    routes = routes :+ new ScalaRoute()
      .path("/headersTest")
      .controller("app.controller.IndexController::headersTest")

    routes = routes :+ new ScalaRoute()
      .path("/requestTest")
      .controller("app.controller.IndexController::requestTest")

    routes = routes :+ new ScalaRoute()
      .path("/blog")
      .controller(s"${app.controller.BlogController.toString}::index")

    routes = routes :+ new ScalaRoute()
      .path("/lucky/luckyNum/{num}")
      .controller("app.controller.LuckyController::luckyNum")*/

    routes
  }
}
