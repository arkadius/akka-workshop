package workshop.syncVsAsync

import java.util.concurrent.{Callable, Executors, ThreadPoolExecutor}
import scala.collection.JavaConverters._

/**
 * @author arkadius
 */
object SyncApproach extends App {

  val hardWorking = new HardWorkingService

  val quick = new QuickService

  def doWork() = {
    hardWorking.hardWork()
    quick.doItQuick()
  }

  println("result: " + doWork())
//  doConcurrent()

  def doConcurrent() {
    def functionToCallable(f: ()=>Unit) = new Callable[Unit] {
      def call() = f()
    }

    val executor = Executors.newFixedThreadPool(2)
    executor.invokeAll(
      Range(0, 4) map { _ => functionToCallable(() =>
        println("result: " + doWork()))
      } asJavaCollection
    )
    executor.shutdown()
  }

}