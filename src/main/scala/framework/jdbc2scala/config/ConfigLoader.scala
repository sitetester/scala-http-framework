package framework.jdbc2scala.config

trait ConfigLoader {

  def loadConfig(path: String): List[(String, List[(String, Any)])]
}
