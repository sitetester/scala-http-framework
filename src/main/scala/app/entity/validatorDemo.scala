package app.entity

import framework.validation.Validator
import framework.validation.constraint.{Constraint, MaxLength, MinLength}

import scala.collection.mutable.ListBuffer

object validatorDemo extends Validator {

  def demo(): Unit = {

    val user = User(1, "admin", "demo", "abc@yahoo.com")

    val cs = ListBuffer[Map[String, ListBuffer[Constraint]]]()
    cs += Map(
      "username" -> ListBuffer(
        new MinLength()
          .minLength(1)
          .value(user.username),
        new MaxLength()
          .maxLength(20)
          .value(user.username)
      )
    )
  }

}
