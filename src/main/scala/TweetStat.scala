import java.sql.Date

sealed trait TweetTrait {
  val Likes: Int
  val Retweets: Int
  val Comments: Int
  val Content: String
  val Date: Date
}

sealed class TweetStat(val Comments : Int, val Retweets: Int, val Likes: Int, val Content: String)
