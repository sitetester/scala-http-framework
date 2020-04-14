package framework.jdbc2scala.generic.query

import java.sql.{ResultSet, SQLException}

import framework.jdbc2scala.generic.connection.ConnectionMaker
import framework.jdbc2scala.generic.schema.DbTable

class QueryBuilder(dbTable: DbTable) {

  val ASC = "ASC"
  val DESC = "DESC"
  private var _sql: String = ""

  def getSql: String = _sql

  def distinct(columns: Seq[String]): QueryBuilder = {
    val cols = s"""DISTINCT `${columns.mkString("`, `")}`"""
    _sql += s"""SELECT $cols FROM `${dbTable.name}`"""

    this
  }

  def select(columns: String = "*"): QueryBuilder = {
    if (columns.contains("*")) {
      _sql += s""" SELECT * FROM `${dbTable.name}`"""
    } else {
      _sql += s""" SELECT $columns FROM `${dbTable.name}`"""
    }

    this
  }

  def where(where: Map[String, Any]): QueryBuilder = {
    // def where(where: Map[String, String] = Map()): Unit = {
    val whereClause =
      where.keys
        .zip(where.values)
        .map(m => s""" `${m._1}` = "${m._2}" """)
        .mkString(" AND ")

    _sql += s""" WHERE $whereClause """

    this
  }

  def whereIn(column: String, in: Seq[Any]): QueryBuilder = {
    _sql += s""" WHERE $column IN (${in.mkString(", ")}) """

    this
  }

  def whereBetween(column: String, between: (Any, Any)): QueryBuilder = {
    _sql += s""" WHERE $column BETWEEN ${between._1} AND  ${between._2}"""

    this
  }

  def limit(limit: Int = 10): QueryBuilder = {
    _sql += s" LIMIT $limit "
    this
  }

  def offset(offset: Int = 0): QueryBuilder = {
    _sql += s" OFFSET $offset "
    this
  }

  def groupBy(column: String): QueryBuilder = {
    _sql += s" GROUP BY $column "
    this
  }

  def having(column: String,
             operator: String = "=",
             value: Any): QueryBuilder = {

    _sql += s" HAVING $column $operator $value"
    this
  }

  def orderBy(column: String, order: String = "ASC"): QueryBuilder = {
    _sql += s" ORDER BY $column $order"
    this
  }

  def orderByMulti(columns: Seq[(String, String)]): QueryBuilder = {
    val order = columns.map(m => m._1 + " " + m._2).mkString(", ")

    _sql += s" ORDER BY $order"
    this
  }

  def runQuery(): Seq[Seq[(String, Any)]] = {
    val sql = _sql

    println("sql ------- " + sql)

    var ret = Seq[Seq[(String, Any)]]()

    val stmt = ConnectionMaker.makeConnection.createStatement()
    try {
      val rs = stmt.executeQuery(sql)

      var cols: Seq[String] = Seq()
      if (sql.contains("*")) {
        cols = allColumns()
      } else {
        cols = extractColumns(sql)
      }

      while (rs.next) {
        var lb = Seq[Any]()

        cols.foreach(c => {
          lb = lb :+ rs.getString(c)
        })

        ret = ret :+ cols.zip(lb)
      }
    } catch {
      case sQLException: SQLException =>
        println(sQLException.getMessage)

    } finally {
      stmt.close()
    }

    ret
  }

  private def extractColumns(sql: String): Seq[String] = {
    var sqlModified = sql.replace("DISTINCT", "")
    sqlModified = sqlModified.replaceAll("`", "")

    val s = sqlModified.indexOf("SELECT") + "SELECT".length
    val f = sqlModified.indexOf("FROM")

    sqlModified = sqlModified.slice(s, f).trim
    var cols = sqlModified.split(',').map(_.trim)

    if (cols.length == 0) {
      allColumns()
    }

    cols = cols.map(c => {
      c.split("as|AS").last.trim
    })

    cols
  }

  private def allColumns(): Seq[String] = {
    dbTable.columns.map(c => c.name)
  }

  def insertRow(data: Seq[(String, Any)]): Unit = {
    val cols = data.map(m => m._1).mkString(", ")
    val values = data.map(m => s""""${m._2}"""").mkString(", ")

    val sql = s"""INSERT INTO ${dbTable.name}( $cols ) VALUES( $values )"""
    runUpdateQuery(sql)
  }

  def updateRow(data: Seq[(String, Any)], where: Seq[(String, Any)]): Unit = {
    val cols = data.map(m => m._1)
    val vals = data.map(m => m._2)

    val set =
      cols.zip(vals).map(x => s"""${x._1} = "${x._2}"""").mkString(", ")

    val whereCols = where.map(m => m._1)
    val whereVals = where.map(m => m._2)

    val whereClause =
      whereCols
        .zip(whereVals)
        .map(x => s"""${x._1} = "${x._2}"""")
        .mkString(" AND ")

    val sql = s"""UPDATE ${dbTable.name} SET $set WHERE $whereClause"""
    println(sql)

    runUpdateQuery(sql)
  }

  def runUpdateQuery(sql: String): Unit = {

    val stmt = ConnectionMaker.makeConnection.createStatement()
    try {
      val rs = stmt.executeQuery(sql)
    } catch {
      case sQLException: SQLException =>
        println(sQLException.getMessage)

    } finally {
      stmt.close()
    }
  }

  def deleteWhere(where: Seq[Map[String, (String, Any)]]): Unit = {
    val whereCols = where.map(m => m.keys.head)
    val whereVals = where.map(m => m.values.head)

    val whereClause =
      whereCols
        .zip(whereVals)
        .map(x => s"""${x._1} ${x._2._1} "${x._2._2}" """)
        .mkString(" AND ")

    val sql = s"""DELETE FROM ${dbTable.name} WHERE $whereClause"""
    println(sql)

    runUpdateQuery(sql)
  }

  def executeQuery(sql: String): Option[ResultSet] = {
    val stmt = ConnectionMaker.makeConnection.createStatement()
    try {
      val rs = stmt.executeQuery(sql)
      Some(rs)
    } catch {
      case sQLException: SQLException =>
        println(sQLException.getMessage)
        None

    }
  }
}
