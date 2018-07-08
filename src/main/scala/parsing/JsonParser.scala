package parsing

import net.liftweb.json._

class JsonParser extends FileParser {
  override def parse(lineIterator: Iterator[String], onBlockRead: Any => Boolean): Boolean = {
    val text = lineIterator.mkString
    val a = JsonParser.parse(text)
    tryParseJson(a, onBlockRead)
  }

  def tryParseJson(jsonObj: Any, onBlockRead: Any => Boolean): Boolean = {
    jsonObj match {
      case m: Map[String, Any] => onBlockRead(m)

      case l: List[Map[String, Any]] =>
        var parsed = onBlockRead(l)

        if (!parsed) return false

        l.foreach { m =>
          parsed = tryParseJson(m, onBlockRead)
          if (!parsed) return false
        }

        true

      case a: JArray => tryParseJson(a.values, onBlockRead)
      case j: JObject => tryParseJson(j.values, onBlockRead)

      case _ => false
    }
  }
}
