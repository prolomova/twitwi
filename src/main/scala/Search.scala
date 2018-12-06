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

    def findTweetsByHashtag (hashtag: String): Unit = {
      val link = "https://twitter.com/search?q=%23" + hashtag + "&src=typd&lang=ru"
      val page = getPage(new URL(link))
      println(page)
      val regex = "tweet js-stream-tweet js-actionable-tweet js-profile-popup-actionable dismissible-content(.)".r
      val links = regex.findAllIn(page)
      println(links)
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
      val regexContent = ("(?s)<link rel=\"preload\".*\\s+<title>(.+)" + "</title>").r
      val content = regexContent.findAllIn(page)
      val regexDate = (tweetId + "\" class=\"tweet-timestamp js-permalink js-nav js-tooltip\" title=\"(\\d{1,2}:\\d{1,2}) - (\\d{1,2}) (\\S{3,4}) (\\d{4})").r
      val date = regexDate.findAllIn(page)
      //println(date.group(4)) хз, как месяц определить
      new Tweet(data.head, data(1), data(2), content.group(1))
    }
  }

  sealed trait TweetTrait {
    val Likes: Int
    val Retweets: Int
    val Comments: Int
    val Content: String
    val Date: Date
  }

  sealed class Tweet(val Comments : Int, val Retweets: Int, val Likes: Int, val Content: String)

  Search.findTweetsByHashtag("cat")
  //print(Search.extract_data(Search.getPage(new URL("https://twitter.com/rihanna/status/1048481053067960321")),
    //"https://twitter.com/rihanna/status/1048481053067960321"))
}
