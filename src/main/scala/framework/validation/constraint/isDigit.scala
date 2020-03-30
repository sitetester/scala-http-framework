package framework.validation.constraint

class isDigit extends Constraint {

  private var _errMsg: String = "Required digit, but found: "

  def errMsg: String = _errMsg
  def errMsg(errMsg: String): isDigit = {
    _errMsg = errMsg
    this
  }

  def value(value: String): isDigit = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val pattern = "[0-9]+"
    val v = _value.toString.matches(pattern)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }

}
