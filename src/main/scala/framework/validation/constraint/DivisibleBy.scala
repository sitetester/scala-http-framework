package framework.validation.constraint

class DivisibleBy extends Constraint {

  private var _numerator: Int = 0
  private var _denominator: Int = 0

  private var _errMsg: String = ""
  def errMsg: String = _errMsg

  def errMsg(errMsg: String): DivisibleBy = {
    _errMsg = errMsg
    this
  }

  def values(numerator: Int, denominator: Int): DivisibleBy = {
    _numerator = numerator
    _denominator = denominator

    this
  }

  def isValid: Boolean = {
    val v = java.lang.Math.floorMod(_numerator, _denominator) == 0
    if (!v) {
      _errMsg = s"${_numerator} can\'t be divided by ${_denominator}"
    }
    v
  }

}
