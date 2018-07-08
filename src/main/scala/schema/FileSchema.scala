package schema

import example.{FileDescriptor, SchemaConfiguration}

import scala.collection.mutable.ArrayBuffer

abstract class FileSchema {
  protected var tests: ArrayBuffer[FileDescriptor => Boolean] = ArrayBuffer()
  protected var blockTests: ArrayBuffer[Any => Boolean] = ArrayBuffer()
  protected var schemaConfig: SchemaConfiguration = _

  def this(schemaConfiguration: SchemaConfiguration) {
    this
    this.schemaConfig = schemaConfiguration

    withBlockTest(testColumnCount)
    withBlockTest(testColumnTypes)
  }

  def withTest(test: FileDescriptor => Boolean): FileSchema = {
    tests += test

    this
  }

  def withBlockTest(test: Any => Boolean): FileSchema = {
    blockTests += test

    this
  }

  def testColumnCount(block: Any): Boolean
  def testColumnTypes(block: Any): Boolean

  def test(fileDescriptor: FileDescriptor): Boolean = {
    var pass = true
    for (test <- tests) {
      pass = test(fileDescriptor)

      if (!pass) return false
    }

    true
  }

  def testBlock(block: Any): Boolean = {
    var pass = true
    for (testBlock <- blockTests) {
      pass = testBlock(block)

      if (!pass) return false
    }

    true
  }
}
