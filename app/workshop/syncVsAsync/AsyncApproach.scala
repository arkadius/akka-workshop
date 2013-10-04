package workshop.syncVsAsync

import java.util.concurrent.{Callable, Executors}
import scala.collection.JavaConverters._
import scala.concurrent._
import duration._

/**
 * @author arkadius
 */
object AsyncApproach extends App {

  val hardWorking = new HardWorkingService

  val quick = new QuickService

  implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(2))

  def doWork() =
    for {
      hardWorkResult <- future { hardWorking.hardWork() }
      quickResult <-    future { quick.doItQuick() }
    } yield quickResult

  Await.result(doWork() map { r => println(s"result: $r") }, 5 seconds); executionContext.shutdown()
//  doConcurrent()

  def doConcurrent() {
    val f = Range(0, 4) map { _ =>
      doWork() map { result =>
        println(s"result: $result")
      }
    }
    Await.result(Future.sequence(f), 10 seconds)
    executionContext.shutdown()
  }

}

