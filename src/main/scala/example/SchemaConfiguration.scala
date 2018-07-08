package example
import java.util

import scala.beans.BeanProperty

class SchemaConfiguration {
  @BeanProperty
  var columnCount: Int = _

  //TODO: try with _
  @BeanProperty
  var columnTypes: java.util.LinkedHashMap[String, String] = new util.LinkedHashMap()

  @BeanProperty
  var xmlObjectNodeName: String = _
}
