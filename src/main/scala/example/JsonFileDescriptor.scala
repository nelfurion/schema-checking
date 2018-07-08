package example

class JsonFileDescriptor extends FileDescriptor {
  protected var _objectsCount: Int = 0
  protected var _propertiesCount: Int = 0

  def objectsCount: Int = _objectsCount
  def propertiesCount: Int = _propertiesCount

  override def update(block: Any): Unit = {
    block match {
      case m: Map[String, Any] =>
        _objectsCount += 1
        _propertiesCount = m.keys.iterator.length
      case _ =>
    }
  }
}
