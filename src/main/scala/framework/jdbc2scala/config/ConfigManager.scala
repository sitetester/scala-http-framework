package framework.jdbc2scala.config

import java.nio.file.{Files, Paths}

object ConfigManager {

  val supportedDbDrivers: List[String] = List("sqlite")
  val supportedConfigFormats: List[String] = List("ini")
  val configReaderMap: List[(String, ConfigLoader)] = List(
    "ini" -> IniConfigLoader
  )
  private var config: List[(String, List[(String, Any)])] = List()

  def loadConfig(): AppConfig = {

    val dir = System.getProperty("user.dir") + "/config/"
    val path = Paths.get(dir)

    val configFile = Files.list(path).limit(1).findFirst().get()

    val fileName = configFile.getFileName.toString
    val extension = fileName.split("\\.").last // e.g. ini

    val readers = configReaderMap.filter(_._1 == extension)
    if (readers.isEmpty) {
      throw new Exception(
        s"No suitable config loader found for extension ($extension)"
      )
    }

    config = readers.head._2.loadConfig(configFile.toString)
    val dbDriver = getDbDriverConfig
    if (dbDriver == "") {
      throw new Exception(s"No supported driver found for DB connection")
    }

    val dbDriverConfig = config.filter(c => c._1 == dbDriver).head._2

    AppConfig(
      DbDriver(
        dbDriver,
        dbDriverConfig.filter(x => x._1 == "classForName").head._2.toString,
        dbDriverConfig.filter(x => x._1 == "url").head._2.toString
      )
    )
  }

  def getDbDriverConfig: String = {
    for (dbDriver <- supportedDbDrivers) {
      val found = config.filter(_._1 == dbDriver)
      if (found.nonEmpty) {
        return dbDriver
      }
    }
    ""
  }
}
