package parsing

object ParsingFixtures {
  def callbacks = new {
    val onBlockReadFails = (a: Any) => false
    val onBlockReadSucceeds = (a: Any) => true
  }
}
