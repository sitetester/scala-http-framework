package framework.validation.constraint

trait Constraint {

  protected var _value: String = ""
  protected var _valid: Boolean = true
  protected var _field: String = ""

  def isValid: Boolean

  def errMsg: String

  def value: String = _value

}
