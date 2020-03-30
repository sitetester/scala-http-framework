package framework.validation.constraint

class Timezone extends Constraint {

  private var _errMsg: String = "Required digit, but found: "

  def errMsg: String = _errMsg
  def errMsg(errMsg: String): Timezone = {
    _errMsg = errMsg
    this
  }

  def value(value: String): Timezone = {
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
