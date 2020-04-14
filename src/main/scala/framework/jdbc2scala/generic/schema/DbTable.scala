package framework.jdbc2scala.generic.schema

case class DbTable(name: String, columns: List[DbColumn], ID: Int = 0)
