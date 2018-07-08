package schema

import common.CommonFixtures
import org.scalatest.{FunSpec, Matchers}

class CsvSchemaSpec extends FunSpec with Matchers {

  def fixture = new {
    val csvSchema = new CsvSchema(CommonFixtures.fixture.schemaConfig, ",")
  }

  describe("CsvSchema") {
    describe("#testColumnTypes") {
      describe("when type is wrong") {
        it ("returns false") {
          fixture.csvSchema.testColumnTypes("1, wrong, correct") should be (false)
          fixture.csvSchema.testColumnTypes("wrong, 1, correct") should be (false)
        }
      }

      describe("when type is correct") {
        it ("returns true") {
          fixture.csvSchema.testColumnTypes("1, 1, correct") should be (true)
        }
      }

      describe ("when line has fewer columns than in schema") {
        it ("returns false") {
          fixture.csvSchema.testColumnTypes("1, 1") should be (false)
          fixture.csvSchema.testColumnTypes("") should be (false)
        }
      }

      describe ("when line has more columns than in schema") {
        it ("returns true") {
          fixture.csvSchema.testColumnTypes("1, 1, string, 123") should be (true)
        }

        it ("returns false") {
          fixture.csvSchema.testColumnTypes("string, 1, string, 123") should be (false)
        }
      }
    }

    describe("#testColumnCount") {
      describe("when column count is different than in schema") {
        it ("returns false") {
          fixture.csvSchema.testColumnCount("string, 1, string, 123") should be (false)
          fixture.csvSchema.testColumnCount("string, 1") should be (false)
          fixture.csvSchema.testColumnCount("") should be (false)
        }
      }

      describe("when column count is correct") {
        it ("returns true") {
          fixture.csvSchema.testColumnCount("any, any, any") should be (true)
        }
      }
    }
  }
}
