import sbtrelease.ReleaseStateTransformations._

name := "multiset"

organization := "com.htmlism"

autoAPIMappings := true

initialCommands in console := "import com.htmlism.multiset._"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

libraryDependencies += "org.specs2" %% "specs2-core" % "3.7.3" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.0" % "test"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.10.6", "2.11.8")

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
