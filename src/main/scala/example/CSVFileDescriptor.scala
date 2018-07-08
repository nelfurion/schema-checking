package example

class CSVFileDescriptor(delimiter: String) extends FileDescriptor {
  protected var _lineCount: Int = 0
  protected var _colCount: Int = 0

  def lineCount: Int = _lineCount
  def colCount: Int = _colCount

  override def update(block: Any): Unit = {
    _lineCount += 1

    val lineColCount = block.asInstanceOf[String].split(delimiter).length
    if (lineColCount > _colCount) {
      _colCount = lineColCount
    }
  }
}
