package framework.validation

import framework.validation.constraint.Constraint

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Validator {

  private var _targetClass: String = ""
  private var _constraints = ListBuffer[Map[String, ListBuffer[Constraint]]]()
  private var _violatedConstraints =
    ListBuffer[Map[String, ListBuffer[Constraint]]]()

  def isValid: Boolean = {
    var valid = true

    _constraints.foreach(m => {
      m.keySet.foreach(k => {
        m.values.foreach(csColl => {
          csColl.foreach(cs => {
            if (!cs.isValid) {
              valid = false

              _violatedConstraints += Map(k -> ListBuffer(cs))
            }
          })
        })
      })
    })

    valid
  }

  def targetClass: String = _targetClass

  def targetClass(targetClass: String): Validator = {
    _targetClass = targetClass
    this
  }

  def constraints: mutable.Seq[Map[String, ListBuffer[Constraint]]] =
    _constraints

  def constraints(
    constraints: ListBuffer[Map[String, ListBuffer[Constraint]]]
  ): Validator = {
    _constraints = constraints
    this
  }

  def violatedConstraints: ListBuffer[Map[String, ListBuffer[Constraint]]] = {
    _violatedConstraints
  }
}
