logLevel := Level.Warn

resolvers += Resolver.sonatypeRepo("snapshots")

resolvers += Resolver.sonatypeRepo("releases")

{
  val v = VersionNumber(sys.props("java.specification.version"))

  // If JDK 8 or lower
  if (v._1.exists(_ == 1) && v._2.exists(_ < 9)) {
    Seq(addSbtPlugin("org.scala-debugger" % "sbt-jdi-tools" % "1.1.1"))
  } else {
    Seq()
  }
}

// Used for building fat jars
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.1.5")

