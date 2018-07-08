package parsing

import scala.xml._

class XmlParser extends FileParser {
  override def parse(lineIterator: Iterator[String], onBlockRead: Any => Boolean): Boolean = {
    var xml: Elem = null

    try {
      xml = XML.loadString(lineIterator.mkString)
    }
    catch {
      case e: SAXParseException => return false
    }

    tryParseXml(xml, onBlockRead)
  }

    def tryParseXml(xml: Any, onBlockRead: Any => Boolean): Boolean = {
      xml match {
        case e: Elem => parseChildrenTags(e, e.nonEmptyChildren, onBlockRead)
        case _ => true
      }
    }

    def parseChildrenTags(parent: NodeSeq, children: Seq[Node], onBlockRead: Any => Boolean): Boolean = {
      var parsed = onBlockRead(parent)

      if (!parsed) return false

      children.foreach{ x =>
        parsed = tryParseXml(x, onBlockRead)

        if (!parsed) return false
      }

      true
    }
}





