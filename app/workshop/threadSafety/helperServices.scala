package workshop.threadSafety

class HardWorkingService {
  def hardWork() = {
    Thread.sleep(2000)
    println("I'm done, uff")
    "1"
  }
}

class QuickService {
  def doItQuick() = {
    println("done.")
    "2"
  }
}

class DangerousService {
  var i = 0
  def doItNotCarefull() = {
    println(s"i: $i")
    if (i % 2 == 0) {
      Thread.sleep(1000)
      if (i % 2 != 0)
        println("wtf?!")
    } else {
      Thread.sleep(500)
      if (i % 2 == 0)
        println("wtf?!")
    }
    i += 1
    i
  }
}