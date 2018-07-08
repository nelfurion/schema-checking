package example

import parsing.FileParser
import schema.FileSchema

class FileChecker(fileSchema: FileSchema, fileParser: FileParser, fileDescriptor: FileDescriptor, filePath: String) {
  var lineNumber = 0

  def check(dropLines: Int): Boolean = {

    var passedTests = fileParser.read(filePath, describeAndTestBlock, dropLines)

    if (!passedTests) return false

    passedTests = fileSchema.test(fileDescriptor)
    if (!passedTests) {
      println(s"file tests failed")
    }

    passedTests
  }

  protected def describeAndTestBlock(block: Any): Boolean = {
    lineNumber += 1

    var passed = fileSchema.testBlock(block)
    if (!passed) {
      println(s"line tests failed for line $lineNumber")
      return false
    }

    fileDescriptor.update(block)

    true
  }
}
