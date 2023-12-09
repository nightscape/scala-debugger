import sbt.Keys._
import sbt._

object DebuggerTest {
  /** Test-specific project settings. */
  val settings = Seq(
    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-api" % "1.7.5",
      "org.slf4j" % "slf4j-log4j12" % "1.7.5" % "test,it",
      "log4j" % "log4j" % "1.2.17" % "test,it",
      "org.scalatest" %% "scalatest" % "3.0.9" % "test,it",
      "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % "test,it"
    )
  )
}
