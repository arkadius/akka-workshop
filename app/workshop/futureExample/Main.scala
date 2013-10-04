package workshop.futureExample

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import duration._

/**
 * @author arkadius
 */
object Main extends App {

  futureUsingPromise()

  def futureUsingPromise() {
    def getFutureUsingPromise() = {
      val p = promise[String]
      new Thread {
        override def run() {
          Thread.sleep(2000)
          p.success("foo")
        }
      }.start()
      p.future
    }

    val f = getFutureUsingPromise()
    val r = Await.result(f, 5 seconds)
    println(s"result: $r")
  }


}
