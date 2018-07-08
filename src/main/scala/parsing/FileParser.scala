package parsing

import scala.io.BufferedSource

abstract class FileParser {
  def parse(lineIterator: Iterator[String], onBlockRead: Any => Boolean): Boolean

  def read(filePath: String, onBlockRead: Any => Boolean, dropLines: Int = 0): Boolean = {
    val source = scala.io.Source.fromFile(filePath)
    val iterator = source.getLines().drop(dropLines)

    var parsedSuccessfully = false

    try
      parsedSuccessfully = parse(iterator, onBlockRead)
    finally
      source.close()

    parsedSuccessfully
  }
}
