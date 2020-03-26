package framework.http.auth

object BasicAuthentication {

  def authenticate(realm: String = "Access to the staging site"): String = {
    s"Authorization: Basic YWxhZGRpbjpvcGVuc2VzYW1l"
  }
}
