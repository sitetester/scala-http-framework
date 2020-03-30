package framework.validation.constraint

class MinLength extends Constraint {

  private var _minLength = 0
  private var _errMsg: String = "Required min Length: "

  def value(value: String): MinLength = {
    _value = value
    this
  }

  def minLength(minLength: Int): MinLength = {
    _minLength = minLength
    this
  }

  def errMsg: String = _errMsg + minLength.toString
  def errMsg(errMsg: String): MinLength = {
    _errMsg = errMsg
    this
  }

  def isValid: Boolean = {
    val v = _value.asInstanceOf[String].length > _minLength
    if (!v) {
      _errMsg = String.format(_errMsg, minLength)
    }
    v
  }

  def minLength: Int = _minLength

}
