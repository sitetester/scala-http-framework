package framework.validation.constraint

class Email extends Constraint {

  private var _errMsg: String = "Invalid email: "

  def errMsg: String = _errMsg
  def errMsg(errMsg: String): Email = {
    _errMsg = errMsg
    this
  }

  def value(value: String): Email = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val pattern = "[a-zA-Z]+([a-zA-Z0-9]+)?@[a-zA-Z]+.[a-zA-Z]+{1,3}"
    val v = _value.toString.matches(pattern)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }

}
