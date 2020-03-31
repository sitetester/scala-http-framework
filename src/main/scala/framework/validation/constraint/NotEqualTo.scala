package framework.validation.constraint

class NotEqualTo extends Constraint {

  protected var _expected: String = ""
  private var _errMsg: String = ""

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): NotEqualTo = {
    _errMsg = errMsg
    this
  }

  def expected(expected: String): NotEqualTo = {
    _expected = expected
    this
  }

  def value(value: String): NotEqualTo = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val v = _value.equals(_expected)
    if (!v) {
      _errMsg = s"Expected (${_expected}), but got (${_value})"
    }
    v
  }

}
