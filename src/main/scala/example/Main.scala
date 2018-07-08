package example

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import parsing.{CsvParser, JsonParser, XmlParser}
import schema.{CsvSchema, FileSchema, JsonSchema, XmlSchema}
import net.liftweb.json._

object Main extends App {
  val delimiter = ","
  val newLineChar = "\n"
  val colCount = 2

  val yaml = new Yaml(new Constructor(classOf[SchemaConfiguration]))

  var schemaText = ""
  val source = scala.io.Source.fromFile("/home/nelfurion/IdeaProjects/untitled/src/main/dataschema.yml")
  try
    schemaText = source.getLines.mkString(newLineChar)
  finally
    source.close()

  val schemaConfig = yaml.load(schemaText).asInstanceOf[SchemaConfiguration]


  val fileSchema: FileSchema = new CsvSchema(schemaConfig, delimiter)

  val filePath = "/home/nelfurion/IdeaProjects/untitled/src/main/passing.csv"
  val checker: FileChecker = new FileChecker(
    fileSchema,
    new CsvParser(),
    new CSVFileDescriptor(delimiter),
    filePath)

  var filePasses = checker.check(1)

  if (filePasses) {
    println(s"File $filePath passed all tests")
  } else {
    println(s"File $filePath failed")
  }

  println("JSON---------------------------------------------------------------------------------------------")

  val jsonFilePath = "/home/nelfurion/IdeaProjects/untitled/src/main/passing.json"
  val jsonChecker: FileChecker = new FileChecker(
    new JsonSchema(schemaConfig),
    new JsonParser(),
    new JsonFileDescriptor,
    jsonFilePath
  )

  filePasses = jsonChecker.check(0)

  if (filePasses) {
    println(s"File $jsonFilePath passed all tests")
  } else {
    println(s"File $jsonFilePath failed")
  }


  println("XML---------------------------------------------------------------------------------------------")

  val xmlFilePath = "/home/nelfurion/IdeaProjects/untitled/src/main/passing.xml"
  val xmlChecker: FileChecker = new FileChecker(
    new XmlSchema(schemaConfig),
    new XmlParser(),
    new XmlFileDescriptor(schemaConfig.xmlObjectNodeName),
    xmlFilePath
  )

  filePasses = xmlChecker.check(0)

  if (filePasses) {
    println(s"File $xmlFilePath passed all tests")
  } else {
    println(s"File $xmlFilePath failed")
  }
}