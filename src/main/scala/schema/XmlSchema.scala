package schema

import cast.TryCast._
import example.SchemaConfiguration

import scala.xml.Elem

class XmlSchema(schemaConfig: SchemaConfiguration) extends FileSchema {
  override def testColumnCount(block: Any): Boolean = {
    val elem = block.asInstanceOf[Elem]
    var dataCount = 0
    if (elem.label == schemaConfig.xmlObjectNodeName) {
      elem.nonEmptyChildren.foreach {
        case ec: Elem => dataCount += 1
        case _ =>
      }

      dataCount == schemaConfig.columnCount
    } else {
      true
    }
  }

  override def testColumnTypes(block: Any): Boolean = {
    val elem = block.asInstanceOf[Elem]
    if (elem.label == schemaConfig.xmlObjectNodeName) {
      if (elem.nonEmptyChildren.groupBy(_.getClass)(classOf[Elem]).length < schemaConfig.columnCount) {
        return false
      }

      elem.nonEmptyChildren.foreach {
        case ec: Elem =>

          if (ec.nonEmptyChildren.length == 1) {
            var passed = true
            val schemaColumntTypeName = schemaConfig.getColumnTypes.get(ec.label)
            val elemValue = ec.text

            if (schemaColumntTypeName == null) return false

            passed = elemValue.tryCastTo(Class.forName(schemaColumntTypeName))

            if (!passed) return false
          }

        case _ =>
      }
    }

    true
  }
}
