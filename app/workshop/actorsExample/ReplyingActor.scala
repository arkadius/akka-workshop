package workshop.actorsExample

import akka.actor._
import scala.util._
import scala.reflect.ClassTag
import TryPatterns._

/**
 * from: http://www.warski.org/blog/2013/05/typed-ask-for-akka/
 * @author arkadius
 */
trait ReplyingActor extends Actor {
  def receive = {
    case m: Replyable[_] if receiveReplyable.isDefinedAt(m) => {
      sender ! Try {
        receiveReplyable(m)
      }.toAkkaStatus
    }
  }

  def receiveReplyable[T]: PartialFunction[Replyable[T], T]
}

trait Replyable[T]

class ReplyingChildActor extends ReplyingActor {
  def receiveReplyable[T]: PartialFunction[Replyable[T], T] = {
    case Increment => 1
  }
}

case object Increment extends Replyable[Int]