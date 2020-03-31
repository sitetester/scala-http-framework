package framework.validation.constraint

class LengthEqualTo extends Constraint {

  private var _length = 0
  private var _errMsg: String = "Required length"

  def length(length: Int): LengthEqualTo = {
    _length = length
    this
  }

  def value(value: String): LengthEqualTo = {
    _value = value
    this
  }

  def errMsg: String = _errMsg

  def isValid: Boolean = {
    val v = _value.asInstanceOf[String].length > _length
    if (!v) {
      _errMsg = s"${_errMsg} ${_length} (${_value})"
    }
    v
  }

}
