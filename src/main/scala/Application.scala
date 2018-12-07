import org.json4s
import org.json4s.JObject

class Application {
  def getHTTP() : String = ???

  def do_twitwi(intervalManager : IntervalManager, parser : TweetParser) : Unit = {
    val http : String = getHTTP()

    val thread = new Thread(new Runnable() {
      override def run(): Unit = {

        while (true){
          val stat : TweetStat = parser.parse_data(http)

          Thread.sleep(intervalManager.getCurrentInterval(stat))
        }
      }
    })

    thread.run()
  }

  def exportStat() : JObject = ???
}


object Application extends App {
  override def main(args: Array[String]): Unit = {
    val thread = new Thread(new Runnable() {
      override def run(): Unit = {

        while (true) {
          println("hello from thread 1")
          Thread.sleep(200)
        }
      }
    })


    val thread2 = new Thread(new Runnable() {
      override def run(): Unit = {

        while (true) {
          println("hello from thread 2")
          Thread.sleep(100)
        }
      }
    })

    thread2.start()
    thread.start()
  }
}
