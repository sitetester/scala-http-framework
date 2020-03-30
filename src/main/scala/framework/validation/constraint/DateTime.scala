package framework.validation.constraint

class DateTime extends Constraint {

  private var _errMsg: String = "Required digit, but found: "

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): DateTime = {
    _errMsg = errMsg
    this
  }

  def value(value: String): DateTime = {
    _value = value
    this
  }

  def isValid: Boolean = {
    true
  }

  /* def isValid: Boolean = {
    val pattern = "[0-9]+"
    val v = _value.matches(pattern)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }*/

}
