import org.json4s
import org.json4s.JObject

object Application extends App {

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
