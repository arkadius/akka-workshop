package workshop.actorsExample

import akka.actor._
import akka.pattern._
import concurrent._
import duration._


/**
 * @author arkadius
 */
object Main extends App {

  val system = ActorSystem("foo")

  import system.dispatcher

  val parent = system.actorOf(Props(new ParentActor(childs = 1, start = 0)), "parent")

  parent ! ()
  system.actorSelection(system / "parent") ! ()

  after(1 second, system.scheduler)(future { system.shutdown() })

}
