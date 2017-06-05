//import sbtassembly.Plugin._
//import AssemblyKeys._

scalaVersion in ThisBuild := "2.12.1"

lazy val `opencv-playground` = project.in(file("."))
  .settings(
    name := "OpenCV Playground",
    version := "1.0"
  )

artifact in(Compile, assembly) := {
  val art = (artifact in(Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in(Compile, assembly), assembly)

lazy val `misc` = project

libraryDependencies in ThisBuild ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.8",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "org.json4s" %% "json4s-native" % "3.5.0"
)

