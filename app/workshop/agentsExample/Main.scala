package workshop.agentsExample

import akka.agent.Agent
import akka.pattern._

/**
 * @author arkadius
 */
object Main extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  val agent = Agent(5)

  agent.alter(_ + 1).onSuccess { case i => println(s"result: $i") }
  println(s"get result: ${agent.get()}")

  Thread.sleep(1000)

  println(s"get result after a while: ${agent.get()}")

}
