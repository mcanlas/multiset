import sbtrelease.ReleaseStateTransformations._

name := "multiset"

organization := "com.htmlism"

console / initialCommands := "import com.htmlism.multiset._"

libraryDependencies += "org.specs2" %% "specs2-core" % "4.9.4" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.3" % "test"

scalaVersion := "2.12.13"

crossScalaVersions := Seq("2.11.12", "2.12.13")

releaseProcess := Seq(
  checkSnapshotDependencies,
  inquireVersions,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion,
  pushChanges
)

scalafmtOnCompile := true
