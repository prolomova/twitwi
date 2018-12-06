import math.max

trait IntervalManager {
  def getCurrentInterval(stat : TweetStat) : Int
}

class TwiIntervalManager(n_interval : Int, k_multiplier : Int) extends IntervalManager {
  val prevStat : TweetStat = null
  var currInterval : Int = 0

  override def getCurrentInterval(stat: TweetStat): Int = {
    if (prevStat == null)
      currInterval = n_interval
    else{
      if (stat == prevStat)
        currInterval = currInterval * k_multiplier
      else
        currInterval = max(n_interval, currInterval / k_multiplier)
    }
    currInterval
  }
}
