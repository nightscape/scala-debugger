import sbt.Keys._

object SbtPlugin {
  /** sbt plugin-specific project settings. */
  val settings = Seq(
    sbtPlugin := true,

    // Force respect (using sbt-doge) of cross scala versions
    scalaVersion := "2.12.18",
    crossScalaVersions := Seq("2.12.18")
  )
}
