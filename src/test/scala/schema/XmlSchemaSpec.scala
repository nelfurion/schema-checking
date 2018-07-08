package schema

import common.CommonFixtures
import org.scalatest.{FunSpec, Matchers}

class XmlSchemaSpec extends FunSpec with Matchers {
  def fixture: Object { val xmlSchema: XmlSchema } = new {
    val xmlSchema = new XmlSchema(CommonFixtures.fixture.schemaConfig)
  }

  describe("XmlSchema") {
    describe("#testColumnTypes") {
      describe("when column types are correct") {
        it ("returns true") {
          fixture.xmlSchema.testColumnTypes(CommonFixtures.xmlFicture.correctObject) should be (true)
          fixture.xmlSchema.testColumnTypes(CommonFixtures.xmlFicture.multipleObjects) should be (true)
        }
      }

      describe ("when column types are wrong") {
        it ("returns false") {
          fixture.xmlSchema.testColumnTypes(CommonFixtures.xmlFicture.wrongColumnTypeSingleObject) should be (false)
        }
      }

      describe("when columns are less") {
        it ("returns false") {
          fixture.xmlSchema.testColumnTypes(CommonFixtures.xmlFicture.lessColumnsSingleObject) should be (false)
        }
      }

      describe("when columns are more") {
        it ("returns false") {
          println(CommonFixtures.xmlFicture.moreColumnsSingleObject)
          fixture.xmlSchema.testColumnTypes(CommonFixtures.xmlFicture.moreColumnsSingleObject) should be (false)
        }
      }
    }

    describe("#testColumnCount") {
      describe("when columns count is the specified in schema") {
        it ("returns true") {
          fixture.xmlSchema.testColumnCount(CommonFixtures.xmlFicture.correctObject) should be (true)
          fixture.xmlSchema.testColumnCount(CommonFixtures.xmlFicture.wrongColumnTypeSingleObject) should be (true)
        }
      }

      describe("when column count is less than specified") {
        it ("returns false") {
          fixture.xmlSchema.testColumnCount(CommonFixtures.xmlFicture.lessColumnsObject) should be (false)
        }
      }

      describe("when column count is higher than specified") {
        it ("returns false") {
          fixture.xmlSchema.testColumnCount(CommonFixtures.xmlFicture.moreColumnsObject) should be (false)
        }
      }
    }
  }
}
