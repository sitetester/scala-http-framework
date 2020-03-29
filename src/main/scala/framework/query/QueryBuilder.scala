package framework.query

/*class QueryBuilder {

  private var _sql: String = ""

  private var _limit: Int = 10
  private var _offset: Int = 0

  def select(columns: String = "*"): QueryBuilder = {
    _sql = s"SELECT $columns "
    this
  }

  def from(table: String): QueryBuilder = {
    _sql += s"FROM $table "

    _sql += s"LIMIT ${_limit} "
    _sql += s"OFFSET ${_offset} "

    this
  }

  def limit(limit: Int = 10): QueryBuilder = {
    this._limit = limit
    this
  }

  def offset(offset: Int = 10): QueryBuilder = {
    _offset = offset
    this
  }

  def getSql: String = {
    _sql
  }

  def executeSql(sql: String): Option[ResultSet] = {
    val stmt = ConnectionMaker.makeConnection.createStatement()

    try {
      return Some(stmt.executeQuery(sql))
    } catch {
      case sQLException: SQLException =>
        println(sQLException.getMessage)
        None

    } finally {
      stmt.close()
    }
  }

  def executeSql2(sql: String): Unit = {
    val stmt = ConnectionMaker.makeConnection.createStatement()

    try {
      val rs = stmt.executeQuery(sql)
      while (rs.next()) {
        println(rs.getString("title"))
      }
    } catch {
      case sQLException: SQLException =>
        println(sQLException.getMessage)
        None

    } finally {
      stmt.close()
    }

  }

}*/
