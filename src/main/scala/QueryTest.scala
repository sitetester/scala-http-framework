/*
import framework.query.QueryBuilder

object QueryTest extends App {

  val qb = new QueryBuilder()
  val sql = qb
    .select()
    .from("blogs")
    .limit(1)
    .getSql

  /*val rs = qb.executeSql(sql).get
  while (rs.next()) {
    println("dsfdsd")
    println(rs.getInt(1))
  }*/

  qb.executeSql2(sql)

}
 */
