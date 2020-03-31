package framework.validation.constraint

class Regex extends Constraint {

  protected var _pattern: String = ""
  private var _errMsg: String = ""

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): Regex = {
    _errMsg = errMsg
    this
  }

  def pattern(pattern: String): Regex = {
    _pattern = pattern
    this
  }

  def value(value: String): Regex = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val v = _value.toString.matches(_pattern)
    if (!v) {
      _errMsg = s"${_value} doesn't match pattern (${_pattern})"
    }
    v
  }

}
