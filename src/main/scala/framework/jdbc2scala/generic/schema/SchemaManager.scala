package framework.jdbc2scala.generic.schema

import java.sql.SQLException

import framework.jdbc2scala.config.ConfigManager
import framework.jdbc2scala.drivers.sqlite.schema.SqliteSchemaBuilder
import framework.jdbc2scala.generic.connection.ConnectionMaker

object SchemaManager {

  def createTable(dbTable: DbTable): Unit = {

    val schemaBuilder = getSchemaBuilder
    val sql = schemaBuilder.createTableSql(dbTable)

    val stmt = ConnectionMaker.makeConnection.createStatement()
    try {

      stmt.executeUpdate(sql)
      System.out.println("\nTable created successfully!")
    } catch {
      case sQLException: SQLException =>
        println(sQLException.getMessage)
    } finally {
      stmt.close()
    }
  }

  private def getSchemaBuilder: SchemaBuilder = {
    val schemaBuilder = ConfigManager.loadConfig().dbDriver.driver match {
      case "sqlite" => SqliteSchemaBuilder
    }

    schemaBuilder
  }
}
