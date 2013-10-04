package workshop.actorsExample

import akka.actor.{Status, Actor}
import akka.actor.Status.Failure

/**
 * @author arkadius
 */
class ChildActor(start: Int) extends Actor {

  println(s"starting up: $start")

  var current = start

  def receive: Actor.Receive = {
    case _ =>
      if (current >= 10)
        throw new IllegalArgumentException("current is too big")
      current += 1
      sender ! current
  }

  override def postStop() {
    println(s"after stop: $start")
  }
}