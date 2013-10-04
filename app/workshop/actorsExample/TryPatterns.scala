package workshop.actorsExample

import scala.util.Try
import akka.actor.{ActorRef, Status}
import scala.concurrent.Future
import scala.util.control.NonFatal

/**
 * @author arkadius
 */
object TryPatterns {

  implicit class TryEnrichment[U](val t: Try[U]) {
    def toAkkaStatus = {
      t recover {
        case NonFatal(t) => Status.Failure(t)
      } get
    }

    def recoverReplyingTo(target: ActorRef) {
      t recover {
        case NonFatal(t) => target ! Status.Failure(t)
      }
    }
  }

  implicit class TryFutureEnrichment[U](val t: Try[Future[U]]) {
    def toFuture = {
      t recover {
        case NonFatal(t) => Future.failed(t)
      } get
    }
  }

}