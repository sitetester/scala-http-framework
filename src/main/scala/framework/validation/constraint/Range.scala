package framework.validation.constraint

class Range extends Constraint {

  private var _range: String = ""
  private var _errMsg: String = ""

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): Range = {
    _errMsg = errMsg
    this
  }

  def range(range: String = "1-10"): Range = {
    _range = range
    this
  }

  def value(value: Int): Range = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val minMax = _range.split("-")
    val min = minMax.head.toInt
    val max = minMax.last.toInt
    val intValue = _value.toString.toInt

    val v = intValue > min && intValue < max
    if (!v) {
      _errMsg = s"Value (${_value}) not in range (${_range})"
    }
    v
  }

}
