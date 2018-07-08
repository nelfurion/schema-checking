package parsing

import common.CommonFixtures
import net.liftweb.json.JsonParser
import org.scalatest.{FunSpec, Matchers}

class JsonParserSpec extends FunSpec with Matchers {
  def fixture = new {
    val parser = new JsonParser()
  }

  describe("CsvParser") {
    describe("#parse") {
      it ("returns false on empty string") {
        fixture.parser.parse("".lines, ParsingFixtures.callbacks.onBlockReadFails) should be (false)
        fixture.parser.parse("".lines, ParsingFixtures.callbacks.onBlockReadSucceeds) should be (false)
      }

      describe("when line parsing fails") {
        it ("returns false") {
          fixture.parser.parse(CommonFixtures.jsonFixture.correctJsonObjectText.lines, ParsingFixtures.callbacks.onBlockReadFails) should be (false)
        }
      }

      describe("when line parsing succeeds") {
        it ("returns true") {
          fixture.parser.parse(CommonFixtures.jsonFixture.correctJsonObjectText.lines, ParsingFixtures.callbacks.onBlockReadSucceeds) should be (true)
        }
      }
    }
  }
}
