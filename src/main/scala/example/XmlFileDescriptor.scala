package example

import scala.xml.Elem

class XmlFileDescriptor(objectNodeName: String) extends FileDescriptor {
  protected var _objectsCount: Int = 0

  def objectsCount: Int = _objectsCount

  override def update(block: Any): Unit = {
    block match {
      case e: Elem =>
        if(e.label == objectNodeName) {
          _objectsCount += 1
        }

      case _ =>
    }
  }
}
