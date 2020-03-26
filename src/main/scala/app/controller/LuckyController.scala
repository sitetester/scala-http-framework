package app.controller

import framework.http.request.Request
import framework.http.response.Response

object LuckyController {

  def index(): Response = {
    val fruits = Seq("Apple", "Orange", "Banana")
    Response(fruits.mkString)
  }

  /*def luckyNum(request: Request, num: Int): Int = {
    println("request..." + request.uri, request.isHttps, request.headers.headers.mkString(", "))
    println("LuckNum = " + num)
    num
  }*/

  def luckyNum(request: Request): Response = {
    Response(
      s"uri: ${request.uri}\nnum: ${request.queryParamAsInt("num").mkString(",")}\n"
    )
  }
}
