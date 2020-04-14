package framework.jdbc2scala.config

case class DbDriver(driver: String, classForName: String, url: String)

case class AppConfig(dbDriver: DbDriver)
