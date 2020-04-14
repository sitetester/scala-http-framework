name := "scala-http-framework"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.7.2"
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
libraryDependencies += "org.yaml" % "snakeyaml" % "1.8"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test"
