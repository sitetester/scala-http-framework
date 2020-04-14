package framework.jdbc2scala.generic

object Scala2Jdbc {

  /*def persistDbTableObj(dbTable: DbTable, user: User): Unit = {
    println("Going to persist DbTable obj...\n")

    val columns = user.productElementNames.toList
    val values = user.productIterator.toList

    var sql = "INSERT INTO " + dbTable.name + "\n("

    sql += columns.mkString(", ") + ") VALUES \n("
    sql += values.map(v => "\"" + v + "\"").mkString(", ")
    sql += ");"

    println(sql)

    val stmt = ConnectionMaker.makeConnection.createStatement()
    try {
      stmt.executeUpdate(sql)
      System.out.println("\nData saved successfully!")
    } catch {
      case sQLException: SQLException =>
        println(sQLException.getMessage)
    } finally {
      stmt.close()
    }
  }

  def updateDbTableObj(dbTable: DbTable, user: User): Unit = {

    val columns = user.productElementNames.toList
    val values = user.productIterator.toList

    var sql = "UPDATE " + dbTable.name + " SET "
    val kv = columns.zip(values)

    sql +=
      kv.map(kv => {
          if (kv._1 != "ID") {
            s"""${kv._1} =  "${kv._2}" """
          } else {
            ""
          }
        })
        .filter(x => !x.isEmpty)
        .mkString(", ")

    sql +=
      kv.filter(kv => kv._1 == "ID")
        .map(kv => {
          " WHERE " + kv._1 + "=" + kv._2
        })
        .mkString

    println(sql)

    val stmt = ConnectionMaker.makeConnection.createStatement()
    try {
      stmt.executeUpdate(sql)
      System.out.println("\nTable updated successfully!")
    } catch {
      case sQLException: SQLException =>
        println(sQLException.getMessage)
    } finally {
      stmt.close()
    }
  }

  def deleteDbTableObj(dbTable: DbTable, user: User): Unit = {
    val sql = "DELETE FROM `" + dbTable.name + "` WHERE ID = " + user.ID
    println(sql)
  }*/
}
