package framework.validation.constraint

class isBooleanFalse extends Constraint {

  private var _errMsg: String = "Required boolean `false` value, but found: "

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): isBooleanFalse = {
    _errMsg = errMsg
    this
  }

  def value(value: Any): isBooleanFalse = {
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
