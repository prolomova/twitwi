name := "practice07"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.11"

scalacOptions ++= Seq(
  "-feature",
  "-language:higherKinds"
)
