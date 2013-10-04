package workshop.threadSafety

import scala.concurrent._
import duration._
import java.util.concurrent.Executors

/**
 * @author arkadius
 */
object FutureApproach extends App {

  val danger = new DangerousService

  val quick = new QuickService

  implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(2))

  def doWork() =
    for {
      quickResult <-  future { quick.doItQuick() }
      dangerResult <- future { danger.doItNotCarefull() }
    } yield dangerResult

  val f = Range(0, 4) map { _ =>
    doWork() map { result =>
      println(s"result: $result")
    }
  }
  Await.result(Future.sequence(f), 10 seconds)
  executionContext.shutdown()


}
