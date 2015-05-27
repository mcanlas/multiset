import sbtrelease.ReleaseStateTransformations._

name := "multiset"

organization := "com.htmlism"

autoAPIMappings := true

initialCommands in console := "import com.htmlism.multiset._"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

libraryDependencies += "org.specs2" %% "specs2" % "2.4.15" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.3" % "test"

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.10.5", "2.11.6")

releaseSettings

ReleaseKeys.releaseProcess := Seq(
  checkSnapshotDependencies,
  inquireVersions,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion,
  pushChanges)
