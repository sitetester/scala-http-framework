package framework.routing

final case class InvalidClassRouteException(
  private val message: String = "No class level route found.",
  private val cause: Throwable = None.orNull
) extends Exception(message, cause)
