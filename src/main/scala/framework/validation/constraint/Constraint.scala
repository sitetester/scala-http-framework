package framework.validation.constraint

trait Constraint {

  protected var _value: Any = _
  protected var _valid: Boolean = true
  protected var _field: String = ""

  def errMsg: String

  def isValid: Boolean

  def value: Any = _value

}
