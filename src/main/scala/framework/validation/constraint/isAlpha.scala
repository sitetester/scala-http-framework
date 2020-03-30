package framework.validation.constraint

class isAlpha extends Constraint {

  private var _minLength = 0
  private var _errMsg: String = "Required alpha, but found: "

  def field: String = _field

  def field(field: String): isAlpha = {
    _field = field
    this
  }

  def minLength(minLength: Int): isAlpha = {
    _minLength = minLength
    this
  }

  def errMsg: String = _errMsg + minLength.toString

  def errMsg(errMsg: String): isAlpha = {
    _errMsg = errMsg
    this
  }

  def value(value: String): isAlpha = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val v = _value.length > _minLength
    if (!v) {
      _errMsg = String.format(_errMsg, minLength)
    }
    v
  }

  def minLength: Int = _minLength

}
