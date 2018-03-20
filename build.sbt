import sbtrelease.ReleaseStateTransformations._

name := "multiset"

organization := "com.htmlism"

initialCommands in console := "import com.htmlism.multiset._"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

libraryDependencies += "org.specs2" %% "specs2-core" % "3.9.5" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.5" % "test"

scalaVersion := "2.12.5"

crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.5")

releaseProcess := Seq(
  checkSnapshotDependencies,
  inquireVersions,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion,
  pushChanges)
