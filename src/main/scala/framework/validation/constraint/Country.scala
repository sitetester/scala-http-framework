package framework.validation.constraint

class Country extends Constraint {

  private var _countries: Map[String, String] =
    Map("LT" -> "Lithuania", "LV" -> "Latvia", "ES" -> "Estonia")

  private var _errMsg: String = "Invalid country: "

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): Country = {
    _errMsg = errMsg
    this
  }

  def value(value: String): Country = {
    _value = value
    this
  }

  def isValid: Boolean = {
    val v = _countries.contains(_value.toString)
    if (!v) {
      _errMsg = _errMsg + _value
    }
    v
  }

}
