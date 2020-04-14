package framework.jdbc2scala.generic.schema

trait SchemaBuilder {

  def createTableSql(dbTable: DbTable): String
}
