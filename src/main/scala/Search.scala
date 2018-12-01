import java.io.InputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import java.sql.Date

import scala.collection.mutable

object Search extends App {

  object Search {
    def getPage(url: URL): String = {
      val in: InputStream = url.openStream()
      val data = in.readAllBytes()
      val str = new String(data, StandardCharsets.UTF_8)
      str
    }

    def extract_data(page: String, url: String): Tweet = {
      val tweetId = url.split('/').toList.last
      val regex = ("(?s)<span class=\"ProfileTweet-actionCount\"\\s+data-tweet-stat-count=\"(\\d+).*?" + tweetId).r
      val comments = regex.findAllIn(page)
      val data: mutable.MutableList[Int] = new mutable.MutableList[Int]
      for (_ <- 0 until 3){
        data += Integer.parseInt(comments.group(1))
        if (comments.hasNext)
          comments.next()
      }
      new Tweet(data.head, data(1), data(2))
    }
  }

  sealed trait TweetTrait {
    val Likes: Int
    val Retweets: Int
    val Comments: Int
  }

  sealed class Tweet(val Comments : Int, val Retweets: Int, val Likes: Int) extends TweetTrait

  print(Search.extract_data(Search.getPage(new URL("https://twitter.com/rihanna/status/1048481053067960321")),
    "https://twitter.com/rihanna/status/1048481053067960321"))
}
