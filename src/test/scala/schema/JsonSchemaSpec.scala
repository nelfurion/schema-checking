package schema

import common.CommonFixtures
import org.scalatest.{FunSpec, Matchers}

class JsonSchemaSpec extends FunSpec with Matchers {
  def fixture = new {
    val jsonSchema = new JsonSchema(CommonFixtures.fixture.schemaConfig)
  }

  describe("JsonSchema") {
    describe("#testColumnTypes") {
      describe("when column types are correct") {
        it ("returns true") {
          fixture.jsonSchema.testColumnTypes(CommonFixtures.jsonFixture.correctObject) should be (true)
        }
      }

      describe ("when column types are wrong") {
        it ("returns false") {
          fixture.jsonSchema.testColumnTypes(CommonFixtures.jsonFixture.wrongColumnTypeObject) should be (false)
        }
      }

      describe("when columns are less") {
        it ("returns false") {
          fixture.jsonSchema.testColumnTypes(CommonFixtures.jsonFixture.lessColumnsObject) should be (false)
        }
      }

      describe("when columns are more") {
        it ("returns false") {
          fixture.jsonSchema.testColumnTypes(CommonFixtures.jsonFixture.moreColumnsObject)should be (false)
        }
      }
    }

    describe("#testColumnCount") {
      describe("when columns count the specified in schema") {
        it ("returns true") {
          fixture.jsonSchema.testColumnCount(CommonFixtures.jsonFixture.correctObject) should be (true)
          fixture.jsonSchema.testColumnCount(CommonFixtures.jsonFixture.wrongColumnTypeObject) should be (true)
        }
      }

      describe("when column count is less than specified") {
        it ("returns false") {
          fixture.jsonSchema.testColumnCount(CommonFixtures.jsonFixture.lessColumnsObject) should be (false)
        }
      }

      describe("when column count is higher than specified") {
        it ("returns false") {
          fixture.jsonSchema.testColumnCount(CommonFixtures.jsonFixture.moreColumnsObject) should be (false)
        }
      }
    }
  }
}