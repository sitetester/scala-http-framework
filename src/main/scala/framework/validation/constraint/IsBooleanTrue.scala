package framework.validation.constraint

class isBooleanTrue extends Constraint {

  private var _errMsg: String = "Required boolean `true` value, but found: "

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): isBooleanTrue = {
    _errMsg = errMsg
    this
  }

  def value(value: Any): isBooleanTrue = {
    _value = value
    this
  }

  def isValid: Boolean = {
    var valid = false
    try {
      valid = _value.asInstanceOf[Boolean].booleanValue == false
    } catch {
      case e: java.lang.ClassCastException =>
        valid = false
        _errMsg = s"${_errMsg} ${_value} (Hint: ${_value.getClass()})"
    }

    valid
  }

}
