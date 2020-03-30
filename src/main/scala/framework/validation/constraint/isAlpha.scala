package framework.validation.constraint

class isAlpha extends Constraint {

  private var _errMsg: String = "Required alpha, but found: "

  def errMsg: String = _errMsg
  def errMsg(errMsg: String): isAlpha = {
    _errMsg = errMsg
    this
  }

  def value(value: String): isAlpha = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val pattern = "[a-zA-Z]+"
    val v = _value.toString.matches(pattern)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }

}
