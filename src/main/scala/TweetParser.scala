import java.net.URL

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

trait TweetParser {
  def parse_data(http : String) : TweetStat
}

class TwiTweetParser(downloader : PageDownloader) extends TweetParser{
  def extract_data(page: String, tweetId: String): TweetStat = {
    //val tweetId = url.split('/').toList.last
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
    new TweetStat(data.head, data(1), data(2), content.group(1))
  }

  def findTweetsByHashtag (hashtag: String): Seq[String] = {
    val link = "https://twitter.com/search?q=%23" + hashtag + "&src=typd&lang=ru"
    val page = downloader.getPage(new URL(link))
    val regex = "tweet js-stream-tweet js-actionable-tweet js-profile-popup-actionable dismissible-content(.)".r

    val ret : Seq[String] = new ListBuffer[String]

    for (link <- regex.findAllIn(page))
      ret.+:(link)

    ret
  }

  override def parse_data(data : String): TweetStat = ???
}