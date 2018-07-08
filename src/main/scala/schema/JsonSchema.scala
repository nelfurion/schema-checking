package schema
import cast.TryCast._
import example.SchemaConfiguration

class JsonSchema(schemaConfig: SchemaConfiguration) extends FileSchema {
  override def testColumnCount(block: Any): Boolean = {
    block match {
      case m: Map[String, Any] => m.keys.iterator.length == schemaConfig.columnCount
      case _ => true
    }
  }

  override def testColumnTypes(block: Any): Boolean = {
    block match {
      case m: Map[String, AnyVal] =>
        var passed = true
        m.foreach {case (key, value) =>
          val schemaColumnTypeName = schemaConfig.getColumnTypes.get(key)
          passed = value.toString.tryCastTo(Class.forName(schemaColumnTypeName))

          if (!passed) return false
        }

        true

      case _ => true
    }

  }
}
