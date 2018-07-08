package common

import example.SchemaConfiguration
import net.liftweb.json.JsonParser
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

import scala.xml.XML

object CommonFixtures {
  def fixture = new {
    val yamlText =
      """
columnCount: 3
columnTypes:
  index: java.lang.Integer
  age: java.lang.Integer
  name: java.lang.String
xmlObjectNodeName: object
      """.stripMargin
    val yaml = new Yaml(new Constructor(classOf[SchemaConfiguration]))
    val schemaConfig = yaml.load(yamlText).asInstanceOf[SchemaConfiguration]
  }

  def xmlFicture = new {
    val correctXmlText =
"""<?xml version="1.0" encoding="UTF-8" ?>
<objects>
    <object>
        <index>1</index>
        <age>11</age>
        <name>asd</name>
    </object>
</objects>""".stripMargin

    val moreColumnCountText =
"""<?xml version="1.0" encoding="UTF-8" ?>
<objects>
    <object>
        <index>1</index>
        <age>11</age>
        <name>asd</name>
        <second>wrong</second>
    </object>
</objects>""".stripMargin

    val moreColumnCountSingleObjectText =
      """<?xml version="1.0" encoding="UTF-8" ?>
<object>
    <index>1</index>
    <age>11</age>
    <name>asd</name>
    <second>wrong</second>
</object>""".stripMargin

    val lessColumnCountText =
"""<?xml version="1.0" encoding="UTF-8" ?>
<objects>
    <object>
        <index>1</index>
        <age>11</age>
    </object>
</objects>""".stripMargin

    val lessColumnCountSingleObjectText =
      """<?xml version="1.0" encoding="UTF-8" ?>
<object>
    <index>1</index>
    <age>11</age>
</object>""".stripMargin

    val wrongColumnTypeSingleObjectText =
"""<?xml version="1.0" encoding="UTF-8" ?>
<object>
      <index>1</index>
      <age>Wrong</age>
      <name>asd</name>
</object>""".stripMargin

    val multipleObjectsText =
"""<?xml version="1.0" encoding="UTF-8" ?>
<objects>
    <object>
        <index>1</index>
        <age>Wrong</age>
        <name>asd</name>
    </object>
    <object>
        <index>1</index>
        <age>Wrong</age>
        <name>asd</name>
    </object>
</objects>""".stripMargin

    val correctObject = XML.loadString(correctXmlText)
    val moreColumnsObject = XML.loadString(moreColumnCountText)
    val moreColumnsSingleObject = XML.loadString(moreColumnCountSingleObjectText)
    val lessColumnsObject = XML.loadString(lessColumnCountText)
    val lessColumnsSingleObject = XML.loadString(lessColumnCountSingleObjectText)
    val wrongColumnTypeSingleObject = XML.loadString(wrongColumnTypeSingleObjectText)
    val multipleObjects = XML.loadString(multipleObjectsText)
  }

  def jsonFixture = new {
    val correctJsonObjectText =
      """
{
    "index": 5,
    "age": 25,
    "name": "☂"
}
      """.stripMargin
    val wrongColumnTypeObjectText =
      """
{
    "index": "wrong",
    "age": 25,
    "name": "☂"
}
      """.stripMargin

    val lessColumnsObjectText =
      """
{
    "index": "asd",
    "age": 25,
}
      """.stripMargin

    val moreColumnsObjectText =
      """
{
    "index": "asd",
    "age": 25,
    "name": "☂",
    "asd": "☂"
}
      """.stripMargin

    val correctObject = JsonParser.parse(correctJsonObjectText).values
    val wrongColumnTypeObject = JsonParser.parse(wrongColumnTypeObjectText).values
    val lessColumnsObject = JsonParser.parse(lessColumnsObjectText).values
    val moreColumnsObject = JsonParser.parse(moreColumnsObjectText).values
  }
}