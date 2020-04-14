package framework.routing

class ScalaRoute extends scala.annotation.StaticAnnotation {

  private var _restriction: String = ""
  private var _path: String = "/"
  private var _controller: String = ""
  private var _methods: Array[String] = Array("GET")
  private var _schemes: Array[String] = Array("http", "https")

  def restriction(restriction: String): ScalaRoute = {
    _restriction = restriction
    this
  }

  def path(path: String): ScalaRoute = {
    _path = path
    this
  }

  def controller(controller: String): ScalaRoute = {
    _controller = controller
    this
  }

  def methods(methods: Array[String]): ScalaRoute = {
    _methods = methods
    this
  }

  def schemes(schemes: Array[String]): ScalaRoute = {
    _schemes = schemes
    this
  }

  override def toString: String = {
    s"path=$path, controller=$controller, methods=${methods.mkString(",")},schemes=${schemes.mkString(",")}"
  }

  def restriction: String = _restriction

  def path: String = _path

  def methods: Array[String] = _methods

  def schemes: Array[String] = _schemes

  def controller: String = _controller

}
