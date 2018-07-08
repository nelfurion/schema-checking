package parsing

import org.scalatest._

class CsvParserSpec extends FunSpec with Matchers {
  def fixture = new {
    val parser = new CsvParser()
  }

  describe("CsvParser") {
    describe("#parse") {
      it ("returns false on empty string") {
        fixture.parser.parse("".lines, ParsingFixtures.callbacks.onBlockReadFails) should be (false)
        fixture.parser.parse("".lines, ParsingFixtures.callbacks.onBlockReadSucceeds) should be (false)
      }

      describe("when line parsing fails") {
        it ("returns false") {
          fixture.parser.parse(" ".lines, ParsingFixtures.callbacks.onBlockReadFails) should be (false)
        }
      }

      describe("when line parsing succeeds") {
        it ("returns true") {
          fixture.parser.parse(" ".lines, ParsingFixtures.callbacks.onBlockReadSucceeds) should be (true)
        }
      }
    }
  }
}