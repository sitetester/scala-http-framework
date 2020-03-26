package framework.http.request

/*object GETRequestHandler extends RequestHandler {
  val controllerPath = "src/main/scala/app.controller/"

  val root: Path = Paths.get(".").toAbsolutePath.normalize()

  override def handleRequest(request: Request): String = {
    val route = RoutingManager.detectRoutingFormat(request)
    if (route.nonEmpty) {
      return RoutingManager.resolveRoute(route.head, request)
    }

    // YamlRoutesLoader.load()
    // RoutesParser.loadAnnotationRoutes()

    var page = ""

    /*
    println(request.uri)

    if (request.uri == "/") {
      page = showController()
    } else {
      val urlParts = request.uri.split("/")
      page = showController(urlParts.slice(1, 2).mkString, urlParts.slice(2, urlParts.length))
    }*/

    /*val pageRegex = "/[a-z]+".r
    val pageRequested = pageRegex.findFirstIn(request.uri).getOrElse("/")


    if (pageRequested == "/") {
      page = showPage()
    } else if (pageRequested != "") {
      page = showController(pageRequested)
    }*/

    page
  }

  def showController(name: String = "index", args: Seq[String] = Seq()): String = {
    println("name --- " + name)

    val homePath = "src/main/scala/app.controller/"
    val controllerName = name.toLowerCase.capitalize + "Controller"

    var controllerPath = homePath + controllerName + ".scala"
    println("controllerPath --- " + controllerPath)

    if (!Files.exists(Paths.get(controllerPath))) {
      controllerPath = root.normalize() + homePath + "/app/public/error/404.html"
    }

    /*
    val classRef = Class.forName(s"app.controller.$controllerName")
    val x = 123
    val method = classRef.getDeclaredMethod("number", x.getClass)
    val reply = method.invoke(classRef, x)
    println("reply ------ " + reply)*/

    println(s"app.controller.$controllerName")
    val classRef = Class.forName(s"app.controller.$controllerName")
    val methods = classRef.getDeclaredMethods

    for (i <- 0 until methods.length) {
      System.out.println(methods(i).toString)
    }
    ""
  }

  def showPage(page: String = "index.html"): String = {

    var file = root + "/src/main/scala/home/app.public/" + page + ".html"
    val path = Paths.get(file)
    if (!Files.exists(path)) {
      file = root.normalize() + "/src/main/scala/home/app.public/404.html"
    }

    val source = Source.fromFile(file)
    source.getLines().mkString
  }
}*/
