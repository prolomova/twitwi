import java.io.InputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import java.sql.Date

import scala.collection.mutable

class PageDownloader{
  def getPage(url: URL): String = {
    val in: InputStream = url.openStream()
    val data = in.readAllBytes()
    val str = new String(data, StandardCharsets.UTF_8)
    str
  }
}
