package workshop.actorsExample

import org.specs2.mutable.Specification
import akka.actor.{Props, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import org.junit.runner.RunWith
import org.scalatest.{WordSpecLike, FunSpec}
import org.scalatest.junit.JUnitRunner
import akka.actor.Status.Failure

/**
 * @author arkadius
 */
@RunWith(classOf[JUnitRunner])
class ChildActorSpec(_system: ActorSystem) extends TestKit(_system) with WordSpecLike with ImplicitSender {

  def this() = this(ActorSystem("ChildActorSpec"))

  "child actor" must {
    "increment counter" in {
      val child = system.actorOf(Props(new ChildActor(0)))
      child ! ()
      expectMsg(1)
      child ! ()
      expectMsg(2)
    }

    "fail because of too big number" in {
      val child = system.actorOf(Props(new ChildActor(10)))
      child ! ()
      expectMsgType[Failure]
    }
  }

}
