package parsing

class CsvParser extends FileParser {
  override def parse(lines: Iterator[String], onLineRead: Any => Boolean): Boolean = {
    var parsedSuccessfully = true

    if (lines.isEmpty) return false

    for (line <- lines) {
      parsedSuccessfully = onLineRead(line)

      if (!parsedSuccessfully) return false
    }

    true
  }
}