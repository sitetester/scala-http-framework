package framework.http

// https://docs.oracle.com/javaee/7/api/javax/servlet/http/Cookie.html
case class Cookie(name: String,
                  value: String,
                  httpOnly: Boolean = true,
                  comment: String = "",
                  path: String = "",
                  domain: String = "",
                  maxAge: Int = 0,
                  versionNumber: Int = 0)
