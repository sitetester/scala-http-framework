package framework.jdbc2scala.generic.metadata

import java.sql.{Connection, ResultSet}

import scala.collection.mutable.ListBuffer

case class Column(name: String, dataType: String)

object MetaData {

  def getAllTables(connection: Connection): Option[List[String]] = {
    val tables = new ListBuffer[String]()

    val rs: ResultSet = connection.getMetaData.getTables(null, null, "%", null)
    while (rs.next) {
      tables += rs.getString(3)
    }

    Some(tables.toList)
  }

  def getAllColumns(connection: Connection, table: String): List[Column] = {
    val columns = new ListBuffer[Column]()

    val rs: ResultSet =
      connection.getMetaData.getColumns(null, null, table, null)
    while (rs.next()) {
      columns += Column(rs.getString(4), rs.getString(6))
    }

    columns.toList
  }
}
