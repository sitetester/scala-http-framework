package framework.jdbc2scala.drivers.sqlite.schema

import framework.jdbc2scala.generic.schema.{DbTable, SchemaBuilder}

object SqliteSchemaBuilder extends SchemaBuilder {

  override def createTableSql(dbTable: DbTable): String = {

    var sql = "\nCREATE TABLE IF NOT EXISTS " + dbTable.name + "(\n"
    sql += "\tID INTEGER PRIMARY KEY,\n"

    for ((column, index) <- dbTable.columns.zipWithIndex) {
      val notNull = if (!column.isNull) "NOT NULL" else ""
      val lastComma = if (!(dbTable.columns.size - index == 1)) {
        ","
      } else {
        ""
      }
      sql += "\t" + column.name + " " + column.dataType
        .toUpperCase() + " " + notNull + lastComma + "\n"
    }

    sql += ");"
    sql
  }
}
