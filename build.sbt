name := "multiset"

organization := "com.htmlism"

version := "0.0.1-SNAPSHOT"

autoAPIMappings := true

initialCommands in console := "import com.htmlism.multiset._"

libraryDependencies += "org.specs2" %% "specs2" % "2.4.6" % "test"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

crossScalaVersions := Seq("2.10.4", "2.11.2")
