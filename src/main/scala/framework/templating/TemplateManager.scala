package framework.templating

import app.config.Config
import framework.http.response.Response

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.reflect.io.File

object TemplateManager {

  def render(view: String, vars: Map[String, String] = Map()): Response = {

    val fullPath = Config.appViewsPath + view + ".html"
    val f = File(fullPath)
    if (!f.exists) {
      throw new Exception(s"Template path doesn't exists $fullPath")
    }

    val source = Source.fromFile(fullPath)
    var tpl = source.getLines.mkString
    source.close()

    vars.keys.foreach(key => {
      val pattern = key.toString + ".+}"
      val regex = pattern.r
      var target = regex.findFirstIn(tpl).getOrElse("")
      var v = vars(key)

      if (target != "") {
        target = target.init

        if (target.contains("|")) {
          val parts = target.split("\\|").drop(1)
          parts.foreach(part => {
            tpl = handlePipe(tpl, key, v, part.trim)
          })
        }
      }
    })

    Response(tpl, 200, Seq(("Content-Type", "text/html; charset=UTF-8")))
  }

  def handlePipe(tpl: String, key: String, v: String, part: String): String = {
    var tplUpdated = ""

    if (!part.contains("(")) {
      val filterAppliedStr = applyFilter(v, part)

      val patternStr = "\\{\\s?+" + key + "\\|" + part + "\\s?+\\}"
      println(patternStr)

      tplUpdated = tpl.replaceAll(patternStr, filterAppliedStr)
    } else {
      val params = part.replace(")", "").split("\\(")
      val filterAppliedStr = applyFunc(params, v)

      val patternStr = "\\{\\s?+" + key + "\\|" + params.head + "\\(" + params.last + "\\)" + "\\s?+\\}"
      println(patternStr)

      tplUpdated = tpl.replaceAll(patternStr, filterAppliedStr)
    }

    tplUpdated
  }

  def applyFilter(v: String, func: String): String = {

    func match {
      case "head"  => v.head.toString
      case "first" => v.head.toString

      case "last" => v.last.toString

      case "length" => v.length.toString
      case "size"   => v.length.toString

      case "upper"      => v.toUpperCase
      case "lower"      => v.toLowerCase
      case "capitalize" => v.capitalize

      case "trim" => v.trim

      case "reverse" => v.reverse
      case "sorted"  => v.sorted

      case "init" => v.init
      case "tail" => v.tail
    }
  }

  def applyFunc(params: Array[String], v: String): String = {
    val func = params.head

    func match {
      case "take"      => v.take(params.last.toInt)
      case "takeRight" => v.takeRight(params.last.toInt)
      case "drop"      => v.drop(params.last.toInt)
      case "dropRight" => v.dropRight(params.last.toInt)

      case "contains" =>
        v.contains(params(1)).toString

      case "replace" =>
        val p = params(1).split(",")
        v.replace(p.head, p.last)

      case "replaceAll" =>
        val p = params(1).split(",")
        v.replaceAll(p.head, p.last)

      case "wordwrap" => {
        var counter = 0

        var start = 0
        var take = params.last.toInt

        var a = ArrayBuffer[String]()
        while (counter <= v.length) {
          a += v.substring(start, start + take)
          start += take

          counter += start
        }

        a.mkString("</br>")
      }
    }
  }
}
