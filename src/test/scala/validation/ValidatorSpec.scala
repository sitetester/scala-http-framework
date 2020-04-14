package validation

import framework.validation.Validator
import framework.validation.constraint.{
  Choice,
  Constraint,
  Country,
  Currency,
  Date,
  DivisibleBy,
  Email,
  EqualTo,
  LengthEqualTo,
  MinLength,
  NotBlank,
  NotEqualTo,
  Range,
  Regex,
  Url,
  isBooleanFalse,
  isBooleanTrue
}
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.mutable.ListBuffer

class ValidatorSpec extends AnyFlatSpec {

  // TODO: split each part into respective test and test accordingly
  it should "blah blah blah" in {

    val lb = ListBuffer[Map[String, ListBuffer[Constraint]]]()
    lb += Map(
      "username" -> ListBuffer(
        new Choice()
          .choice(Seq("1", "3"))
          .value("2"),
        new Country()
          .value("XYZ"),
        new Country()
          .value("LT"),
        new Currency()
          .value("bogus"),
        new Currency()
          .value("EUR"),
        new Date()
          .value("aa"),
        new DivisibleBy()
          .values(16, 5),
        new DivisibleBy()
          .values(15, 5),
        new Email()
          .value("aa"),
        new Email()
          .value("aa@yahoo.com"),
        new Email()
          .value("sitetester2010@yahoo.com"),
        new EqualTo()
          .value("sun")
          .expected("sun"),
        new isBooleanFalse()
          .value("su"),
        new isBooleanFalse()
          .value("false"),
        new isBooleanFalse()
          .value(false),
        new isBooleanTrue()
          .value("true"),
        new isBooleanTrue()
          .value(true),
        new isBooleanTrue()
          .value(true),
        new LengthEqualTo()
          .length(5)
          .value("ddsdfsff"),
        new LengthEqualTo()
          .length(5)
          .value("abc"),
        new MinLength()
          .minLength(5)
          .value("d"),
        new NotBlank()
          .value(""),
        new NotEqualTo()
          .expected("abc")
          .value("11"),
        new Range()
          .range("10-20")
          .value(14),
        new Range()
          .range("10-20")
          .value(4),
        new Regex()
          .pattern("\\d+")
          .value("14"),
        new Regex()
          .pattern("\\d+")
          .value("ABC"),
        new Regex()
          .pattern("\\d")
          .value("7"),
        new Regex()
          .pattern("[a-z]+")
          .value("abcD"),
        new Url()
          .value("http://www.yahoo.com"),
        new Url()
          .value("http://www.ss.com"),
      )
    )

    val validator = new Validator()
    validator.constraints(lb).isValid
    validator.violatedConstraints.foreach(vc => {

      vc.values.foreach(lb => {
        lb.foreach(c => {
          println(vc.keys.head + ": " + c.errMsg)
        })
      })
    })
  }
}
