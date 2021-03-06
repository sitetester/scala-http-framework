package framework.validation.constraint

class MaxLength extends Constraint {

  private var _maxLength = 0
  private var _errMsg: String = "Required max Length: "

  override def value: Any = super.value
  def value(value: String): MaxLength = {
    _value = value
    this
  }

  def maxLength(maxLength: Int): MaxLength = {
    _maxLength = maxLength
    this
  }

  def errMsg: String = _errMsg + maxLength.toString

  def errMsg(errMsg: String): MaxLength = {
    _errMsg = errMsg
    this
  }

  def isValid: Boolean = {
    val v = _value.asInstanceOf[String].length < _maxLength
    if (!v) {
      _errMsg = String.format(_errMsg, maxLength)
    }
    v
  }

  def maxLength: Int = _maxLength

}
