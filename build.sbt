import sbtrelease.ReleaseStateTransformations._

name := "multiset"

organization := "com.htmlism"

initialCommands in console := "import com.htmlism.multiset._"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

libraryDependencies += "org.specs2" %% "specs2-core" % "4.8.1" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.2" % "test"

scalaVersion := "2.12.10"

crossScalaVersions := Seq("2.11.12", "2.12.10")

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
