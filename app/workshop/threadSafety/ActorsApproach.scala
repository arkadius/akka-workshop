package workshop.threadSafety

import scala.concurrent._
import duration._
import akka.actor.{Props, ActorSystem}
import akka.pattern._
import akka.util.Timeout

/**
 * @author arkadius
 */
object ActorsApproach extends App {

  val system = ActorSystem("fooSystem")

  val danger = system.actorOf(Props(new DangerousActor))

  import system.dispatcher

  val quick = new QuickService

  implicit val timeout = Timeout(5 seconds)

  def doWork() =
    for {
      quickResult <-  future { quick.doItQuick() }
      dangerResult <- danger ? DoWork
    } yield dangerResult

  val f = Range(0, 4) map { _ =>
    doWork() map { result =>
      println(s"result: $result")
    }
  }
  Await.result(Future.sequence(f), 10 seconds)
  system.shutdown()
  system.awaitTermination()

}
