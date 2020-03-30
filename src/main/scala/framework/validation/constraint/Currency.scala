package framework.validation.constraint

class Currency extends Constraint {

  private val _currencies: Seq[String] = Seq("EUR", "USD")

  private var _errMsg: String = "Invalid currency: "

  def errMsg: String = _errMsg
  def errMsg(errMsg: String): Currency = {
    _errMsg = errMsg
    this
  }

  def value(value: String): Currency = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val v = _currencies.contains(_value.toString)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }

}
