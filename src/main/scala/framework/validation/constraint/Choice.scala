package framework.validation.constraint

class Choice extends Constraint {

  protected var _errMsg: String = "Required one of choice values"
  private var _choice: Seq[Any] = Seq()

  def errMsg: String = _errMsg

  def errMsg(errMsg: String): Constraint = {
    _errMsg = errMsg
    this
  }

  def value(value: Any): Choice = {
    _value = value
    this
  }

  def choice(choice: Seq[Any]): Choice = {
    _choice = choice
    this
  }

  def isValid: Boolean = {
    val v = _choice.contains(_value)
    if (!v) {
      _errMsg = s"${_errMsg} (${_choice.mkString(", ")}), but found: ${_value}"
    }
    v
  }

}
