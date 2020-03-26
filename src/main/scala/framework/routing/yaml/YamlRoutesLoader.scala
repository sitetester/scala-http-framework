package framework.routing.yaml

import org.yaml.snakeyaml.Yaml

object YamlRoutesLoader {
  val path = "src/main/scala/"

  def load(): Unit = {

    val source = io.Source.fromFile(path + "app/config/routing/routing.yaml")
    val routes = source.getLines().mkString("\n")

    val all = new Yaml().loadAll(routes)
    all.forEach(a => println(a))

  }

}
