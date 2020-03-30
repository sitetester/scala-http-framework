package framework.validation.constraint

class isAlphaNumeric extends Constraint {

  private var _errMsg: String = "Required alphaNumeric, but found: "

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): isAlphaNumeric = {
    _errMsg = errMsg
    this
  }

  def value(value: String): isAlphaNumeric = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val pattern = "[a-zA-Z0-9]+"
    val v = _value.toString.matches(pattern)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }

}
