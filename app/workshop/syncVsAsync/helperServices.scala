package workshop.syncVsAsync

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