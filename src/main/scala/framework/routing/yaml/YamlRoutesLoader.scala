package framework.routing.yaml

import app.config.Config
import org.yaml.snakeyaml.Yaml

object YamlRoutesLoader {

  def load(): Unit = {

    val source =
      io.Source.fromFile(
        Config.srcMainScalaPath + "app/config/routing/routing.yaml"
      )
    val routes = source.getLines().mkString("\n")

    val all = new Yaml().loadAll(routes)
    all.forEach(a => println(a))

  }

}
