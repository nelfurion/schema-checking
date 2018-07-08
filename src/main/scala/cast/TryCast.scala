package cast

trait TryCast[T] {
  def tryCastTo(value: T, classType: Class[_]): Boolean
}

object TryCast {
  def tryCastTo[T:TryCast](t: T, classType: Class[_]): Boolean = TryCast[T].tryCastTo(t, classType)
  def apply[T](implicit cast: TryCast[T]): TryCast[T] = cast

  def castTo(value: String, className: String): Boolean = {
    val I = classOf[java.lang.Integer].getCanonicalName
    val D = classOf[java.lang.Double].getCanonicalName
    val F = classOf[java.lang.Float].getCanonicalName
    val S = classOf[java.lang.String].getCanonicalName

    try {
      className match {
        case I => value.toInt
        case D => value.toDouble
        case F => value.toFloat
        case S => value.toString
      }

      true
    } catch {
      case e: MatchError => false
      case e: NumberFormatException => false
    }
  }

  implicit class TryCastOps[T: TryCast](t: T) {
    def tryCastTo(classType: Class[_]): Boolean = TryCast[T].tryCastTo(t, classType)
  }

  implicit val stringCast: TryCast[String] =
    new TryCast[String] {
      def tryCastTo(value: String, classType: Class[_]): Boolean = {
        val canonicalName:String = classType.getCanonicalName
        castTo(value, canonicalName)
      }
    }
}
