package framework.validation.constraint

class Url extends Constraint {

  private var _errMsg: String = "Required URL, but found: "

  def errMsg: String = _errMsg
  def errMsg(errMsg: String): Url = {
    _errMsg = errMsg
    this
  }

  def value(value: String): Url = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val pattern = "http(s)?://(www)?.[a-z]+.com"
    val v = _value.toString.matches(pattern)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }

}
