package framework.validation.constraint

class NegativeOrZero extends Constraint {

  private var _errMsg: String = "Required digit, but found: "

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): NegativeOrZero = {
    _errMsg = errMsg
    this
  }

  def value(value: String): NegativeOrZero = {
    _value = value
    this
  }

  def isValid: Boolean = {
    true
  }

  /*def isValid: Boolean = {
    val pattern = "[0-9]+"
    val v = _value.matches(pattern)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }*/

}