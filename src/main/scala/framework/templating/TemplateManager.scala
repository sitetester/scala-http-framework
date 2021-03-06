package framework.templating

import java.text.SimpleDateFormat

import framework.http.request.Request
import framework.http.response.Response

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.reflect.io.File

object TemplateManager {

  def render(fullPath: String, vars: Map[String, Any] = Map()): Response = {
    val f = File(fullPath)
    if (!f.exists) {
      throw new Exception(s"Template path doesn't exists $fullPath")
    }

    val source = Source.fromFile(fullPath)
    var tpl = source.getLines.mkString
    source.close()

    vars.keys.foreach(key => {
      val pattern = key.toString + ".+}}"
      val regex = pattern.r
      var target = regex.findFirstIn(tpl).getOrElse("")
      val v = vars(key)

      if (target != "") {
        target = target.replace("}}", "").trim

        if (target.contains("|")) {
          val parts = target.split("\\|").drop(1)
          parts.foreach(part => {
            tpl = handlePipe(tpl, key, v, part.trim)
          })

        } else if (target.contains(".")) {
          tpl = handleDot(tpl, target.trim, v)

        } else {
          var result = "\\{\\{\\s?+(.+)(\\((.+)\\))?\\s?+\\}\\}".r
            .findAllIn(tpl)
            .matchData
            .foreach { m =>
              val expression = m.group(0)
              tpl = tpl.replace(expression, v.toString)
            }
        }
      }
    })

    Response(tpl, 200, Seq(("Content-Type", "text/html; charset=UTF-8")))
  }

  def handleDot(tpl: String, target: String, v: Any): String = {
    val key = target.split("\\.").last

    if (v.isInstanceOf[Request]) {
      val m = v.getClass.getDeclaredMethod(key)
      m.setAccessible(true)
      val r = m.invoke(v)
      m.setAccessible(false)
      r.toString
    } else {
      val f = v.getClass.getDeclaredField(key)
      f.setAccessible(true)
      val r = (f.getName, f.get(v))
      f.setAccessible(false)
      r._2.toString
    }
  }

  def handlePipe(tpl: String, key: String, v: Any, part: String): String = {
    var tplUpdated = ""

    if (!part.contains("(")) {
      val filterAppliedStr = applyFilter(v.toString, part)

      val patternStr = "\\{\\{\\s?+" + key + "\\|" + part + "\\s?+\\}\\}"
      tplUpdated = tpl.replaceAll(patternStr, filterAppliedStr)
    } else {
      val params = part.replace(")", "").split("\\(")
      val filterAppliedStr = applyFunc(params, v)

      val patternStr = "\\{\\{\\s?+" + key + "\\|" + params.head + "\\(" + params.last + "\\)" + "\\s?+\\}\\}"

      tplUpdated = tpl.replaceAll(patternStr, filterAppliedStr)
    }

    tplUpdated
  }

  def applyFilter(v: String, func: String): String = {

    func match {
      case "upper"      => v.toUpperCase
      case "lower"      => v.toLowerCase
      case "capitalize" => v.capitalize

      case "first" => v.head.toString
      case "last"  => v.last.toString

      case "length" => v.length.toString

      case "trim" => v.trim

      case "reverse" => v.reverse
      case "sorted"  => v.toSeq.sorted.unwrap
    }
  }

  /*def applyTest(): Unit = {
    val func = params.head
    func match {
      case "contains" =>
        v.contains(params(1)).toString
    }
  }*/

  def applyFunc(params: Array[String], v: Any): String = {
    val func = params.head
    val vStr = v.toString

    func match {
      case "take"      => vStr.take(params.last.toInt)
      case "takeRight" => vStr.takeRight(params.last.toInt)
      case "drop"      => vStr.drop(params.last.toInt)
      case "dropRight" => vStr.dropRight(params.last.toInt)

      case "replace" =>
        val p = params(1).split(",")
        vStr.replace(p.head, p.last)

      case "replaceAll" =>
        val p = params(1).split(",")
        vStr.replaceAll(p.head, p.last)

      case "wordwrap" =>
        var counter = 0

        var start = 0
        var take = params.last.toInt

        var a = ArrayBuffer[String]()
        while (counter <= vStr.length) {
          a += vStr.substring(start, start + take)
          start += take

          counter += start
        }

        a.mkString("</br>")

      case "date" =>
        val sdf = new SimpleDateFormat(params.last)
        sdf.format(v)
    }
  }

  def handleFuncWithoutPipe(func: String, arg: String): String = {
    func match {
      case "asset" => "http://localhost:8081/public/" + arg
    }
  }
}
