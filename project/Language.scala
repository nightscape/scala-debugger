import sbt.Keys._
import sbt._

object Language {
  /** Language-specific project settings. */
  val settings = Seq(
    libraryDependencies ++= Seq(
      "org.parboiled" %% "parboiled" % "2.1.8",
      "org.scalatest" %% "scalatest" % "3.0.9" % "test,it",
      "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % "test,it"
    )
  )
}
