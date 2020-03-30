package framework.validation.constraint

class NotBlank extends Constraint {

  private var _errMsg: String = "Required not blank"

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): NotBlank = {
    _errMsg = errMsg
    this
  }

  def value(value: String): NotBlank = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val v = _value.toString.trim.length > 0
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }

}
