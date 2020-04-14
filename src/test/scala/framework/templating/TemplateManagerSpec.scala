package framework.templating

import java.text.SimpleDateFormat

import org.scalatest.flatspec.AnyFlatSpec

class TemplateManagerSpec extends AnyFlatSpec {

  val basePath = "src/test/resources/"

  // FILTERS
  it should "render view" in {
    val fullPath = basePath + "templating/views/twig/msg.html"
    val rs =
      TemplateManager.render(fullPath, Map("msg" -> "Scala is awesome"))
    assert(rs.rsData == "The messages says: Scala is awesome")
  }

  it should "apply `upper` filter" in {
    val fullPath = basePath + "templating/views/twig/filters/upper.html"
    val rs =
      TemplateManager.render(fullPath, Map("msg" -> "Scala is awesome"))
    assert(rs.rsData == "The messages says: SCALA IS AWESOME")
  }

  it should "apply `lower` filter" in {
    val fullPath = basePath + "templating/views/twig/filters/lower.html"
    val rs =
      TemplateManager.render(fullPath, Map("msg" -> "Scala is awesome"))
    assert(rs.rsData == "The messages says: scala is awesome")
  }

  it should "apply `capitalize` filter" in {
    val fullPath = basePath + "templating/views/twig/filters/capitalize.html"
    val rs = TemplateManager.render(fullPath, Map("msg" -> "Scala is awesome"))
    assert(rs.rsData == "The messages says: Scala is awesome")
  }

  it should "apply `first` filter" in {
    val fullPath = basePath + "templating/views/twig/filters/first.html"
    val rs = TemplateManager.render(fullPath, Map("msg" -> "Hi"))
    assert(rs.rsData == "The messages says: H")
  }

  it should "apply `last` filter" in {
    val fullPath = basePath + "templating/views/twig/filters/last.html"
    val rs = TemplateManager.render(fullPath, Map("msg" -> "Hi"))
    assert(rs.rsData == "The messages says: i")
  }

  it should "apply `length` filter" in {
    val fullPath = basePath + "templating/views/twig/filters/length.html"
    val rs = TemplateManager.render(fullPath, Map("msg" -> "Hi"))
    assert(rs.rsData == "The messages says: 2")
  }

  it should "apply `trim` filter" in {
    val fullPath = basePath + "templating/views/twig/filters/trim.html"
    val rs = TemplateManager.render(fullPath, Map("msg" -> "Hi "))
    assert(rs.rsData == "The messages says: Hi")
  }

  it should "apply `reverse` filter" in {
    val fullPath = basePath + "templating/views/twig/filters/reverse.html"
    val rs = TemplateManager.render(fullPath, Map("msg" -> "Hi"))
    assert(rs.rsData == "The messages says: iH")
  }

  it should "apply `sorted` filter" in {
    val fullPath = basePath + "templating/views/twig/filters/sorted.html"
    val rs = TemplateManager.render(fullPath, Map("msg" -> "dcab"))
    assert(rs.rsData == "The messages says: abcd")
  }

  // FUNCTIONS
  it should "apply `take` function" in {
    val fullPath = basePath + "templating/views/twig/functions/take.html"
    val rs = TemplateManager.render(fullPath, Map("msg" -> "Scala is awesome"))
    assert(rs.rsData == "The messages says: Scala")
  }

  it should "apply `takeRight` function" in {
    val fullPath = basePath + "templating/views/twig/functions/takeRight.html"
    val rs = TemplateManager.render(fullPath, Map("msg" -> "Scala is awesome"))
    assert(rs.rsData == "The messages says: awesome")
  }

  it should "apply `drop` function" in {
    val fullPath = basePath + "templating/views/twig/functions/drop.html"
    val rs =
      TemplateManager.render(fullPath, Map("msg" -> "Java|Scala is awesome"))
    assert(rs.rsData == "The messages says: Scala is awesome")
  }

  it should "apply `dropRight` function" in {
    val fullPath = basePath + "templating/views/twig/functions/dropRight.html"
    val rs =
      TemplateManager.render(fullPath, Map("msg" -> "Java|Scala is awesome"))
    assert(rs.rsData == "The messages says: Java|Scala")
  }

  it should "apply `replace` function" in {
    val fullPath = basePath + "templating/views/twig/functions/replace.html"
    val rs =
      TemplateManager.render(fullPath, Map("msg" -> "Java|Scala is awesome"))
    assert(rs.rsData == "The messages says: Python|Scala is awesome")
  }

  it should "apply `replaceAll` function" in {
    val fullPath = basePath + "templating/views/twig/functions/replaceAll.html"
    val rs =
      TemplateManager.render(
        fullPath,
        Map("msg" -> "JS is NICE language. Really NICE ?")
      )
    assert(rs.rsData == "The messages says: JS is SHIT language. Really SHIT ?")
  }

  it should "apply `wordwrap` function" in {
    val fullPath = basePath + "templating/views/twig/functions/wordwrap.html"
    val rs =
      TemplateManager.render(
        fullPath,
        Map(
          "msg" -> "Scala is awesome.Scala is awesome.Scala is awesome.Scala is awesome."
        )
      )
    assert(
      rs.rsData == "Scala is awesome.</br>Scala is awesome.</br>Scala is awesome."
    )
  }

  // TODO: Check again and fix
  it should "apply `date` function" in {
    val fullPath = basePath + "templating/views/twig/functions/date.html"
    val dt = new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-20")
    val rs =
      TemplateManager.render(fullPath, Map("date" -> dt.toString))
    assert(rs.rsData == "The messages says: Thu Jan 20 00:00:00 EET 2011")
  }

}
