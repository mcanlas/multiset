import sbtrelease.ReleaseStateTransformations._

name := "multiset"

organization := "com.htmlism"

initialCommands in console := "import com.htmlism.multiset._"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

libraryDependencies += "org.specs2" %% "specs2-core" % "4.6.0" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"

scalaVersion := "2.12.8"

crossScalaVersions := Seq("2.11.12", "2.12.8")

releaseProcess := Seq(checkSnapshotDependencies,
                      inquireVersions,
                      runTest,
                      setReleaseVersion,
                      commitReleaseVersion,
                      tagRelease,
                      setNextVersion,
                      commitNextVersion,
                      pushChanges)

scalafmtOnCompile := true
