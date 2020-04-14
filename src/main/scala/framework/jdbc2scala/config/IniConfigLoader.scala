package framework.jdbc2scala.config

import scala.collection.mutable.ListBuffer

object IniConfigLoader extends ConfigLoader {

  override def loadConfig(path: String): List[(String, List[(String, Any)])] = {

    val source = scala.io.Source.fromFile(path)

    val str = try {
      source.mkString
    } finally {
      source.close()
    }

    var section = ""
    var keyValueTupleList = ListBuffer[(String, Any)]()
    var sectionValuesMap = ListBuffer[(String, ListBuffer[(String, Any)])]()

    str
      .split("\n")
      .zipWithIndex
      .filterNot(line => {
        val value = line._1.trim
        value.isEmpty || value.startsWith(";")
      })
      .foreach(line => {
        val value = line._1.trim

        if (value.head == '[') {
          keyValueTupleList = ListBuffer[(String, Any)]() // reset

          section = line._1
            .replaceAll("\\[", "")
            .replaceAll("\\]", "")

        } else {
          val kv = value
            .split("=")
            .map(_.trim)

          if (kv.length > 1) {
            keyValueTupleList = keyValueTupleList :+ (kv(0), kv(1))

            val sectionExists = sectionValuesMap.count(m => m._1 == section)

            if (sectionExists == 0) {
              sectionValuesMap += (section -> keyValueTupleList)
            } else {
              for ((m, i) <- sectionValuesMap.zipWithIndex) {
                if (m._1 == section) {
                  sectionValuesMap(i) = (section, keyValueTupleList)
                }
              }
            }
          }
        }
      })

    val toList = sectionValuesMap.toList
    toList.map(x => (x._1, x._2.toList))
  }
}
