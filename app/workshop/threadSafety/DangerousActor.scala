package workshop.threadSafety

import akka.actor.Actor

/**
 * @author arkadius
 */
class DangerousActor extends Actor {
  val service = new DangerousService

  def receive: Actor.Receive = {
    case DoWork =>
      sender ! service.doItNotCarefull()
  }
}

case object DoWork
