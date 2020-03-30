package framework.validation.constraint

import java.text.{ParseException, SimpleDateFormat}

class Date extends Constraint {

  private var _format: String = "yyyy-MM-dd"
  private var _errMsg: String = "Couldn't format date "

  def errMsg: String = _errMsg
  def errMsg(errMsg: String): Date = {
    _errMsg = errMsg
    this
  }

  def value(value: String): Date = {
    _value = value
    this
  }

  def format(format: String): Date = {
    _format = format
    this
  }

  def isValid: Boolean = {
    try {
      val sdf = new SimpleDateFormat(_format)
      sdf.parse(_value.toString)
      return true
    } catch {
      case _: ParseException =>
        _errMsg = s"${_errMsg}(${_value}) per format (${_format})"
        return false
    }

    true
  }

}
