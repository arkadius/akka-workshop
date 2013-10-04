package workshop.actorsExample

import akka.actor._
import SupervisorStrategy._
import scala.concurrent._
import duration._
import akka.util.Timeout
import akka.pattern._
import scala.util._
import akka.routing.FromConfig

/**
 * @author arkadius
 */
class ParentActor(childs: Int, start: Int = 0) extends Actor {

  println("starting parent")

  val childActors = Range(0, childs) map { i => context.actorOf(Props(new ChildActor(start + i)), s"child_$i") }

  import context.dispatcher

  def receive: Actor.Receive = {
    case _ =>
      implicit val timeout = Timeout(1 second)
      Future.sequence(childActors map { _ ? () }) onComplete {
        case Success(r)  => println(s"result: $r")
        case Failure(ex) => println(s"failure: ${ex.getMessage}")
      }
  }

  override def postStop() {
    println("after stop: parent")
  }

//  override val supervisorStrategy: SupervisorStrategy = OneForOneStrategy(maxNrOfRetries = 0, withinTimeRange = 1 minute) {
//    case _: IllegalArgumentException => Escalate
//  }
}
