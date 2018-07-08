package schema
import example.{CSVFileDescriptor, FileDescriptor, SchemaConfiguration}
import cast.TryCast._

//TODO:
import scala.collection.JavaConversions._

class CsvSchema(schemaConfig: SchemaConfiguration) extends FileSchema {
  var cellDelimiter: String = _

  def this(schemaConfiguration: SchemaConfiguration, cellDelimiter: String) {
    this(schemaConfiguration)
    this.cellDelimiter = cellDelimiter
  }

  override def testColumnCount(line: Any): Boolean = {
    line.asInstanceOf[String].split(cellDelimiter).length == schemaConfig.columnCount
  }

  override def testColumnTypes(block: Any): Boolean = {
    val line = block.asInstanceOf[String]
    val values = line.split(",").map(_.trim)
    val lineColumnsIterator = values.iterator

    var passed = true
    for ((columnName, valueType) <- schemaConfig.columnTypes) {
      val columnValueTypeName: String = valueType

      if (lineColumnsIterator.isEmpty) return false

      val nextValue: String = lineColumnsIterator.next

      passed = nextValue.tryCastTo(Class.forName(columnValueTypeName))

      if(!passed)  {
        println(s"column type wrong for column: $columnName")
        println(s"cell data cannot be cast to $columnValueTypeName")

        return false
      }
    }

    true
  }
}