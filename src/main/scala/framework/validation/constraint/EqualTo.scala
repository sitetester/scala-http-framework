package framework.validation.constraint

class EqualTo extends Constraint {

  private var _errMsg: String = "Required:  "
  private var _expected: String = ""

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): EqualTo = {
    _errMsg = errMsg
    this
  }

  def expected(expected: String): EqualTo = {
    _expected = expected
    this
  }

  def value(value: String): EqualTo = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val v = _value.toString.equalsIgnoreCase(_expected)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }

}
