import sbt.Resolver

import Common._
import app.softnetwork.sbt.build._

/////////////////////////////////
// Defaults
/////////////////////////////////

app.softnetwork.sbt.build.Publication.settings

/////////////////////////////////
// Useful aliases
/////////////////////////////////

addCommandAlias("cd", "project") // navigate the projects

addCommandAlias("cc", ";clean;compile") // clean and compile

addCommandAlias("pl", ";clean;publishLocal") // clean and publish locally

addCommandAlias("pr", ";clean;publish") // clean and publish globally

addCommandAlias("pld", ";clean;local:publishLocal;dockerComposeUp") // clean and publish/launch the docker environment

addCommandAlias("dct", ";dockerComposeTest") // navigate the projects

ThisBuild / shellPrompt := prompt

ThisBuild / organization := "app.softnetwork"

name := "generic-sequence-api"

ThisBuild / version := "0.1.3"

ThisBuild / scalaVersion := "2.12.15"

ThisBuild / scalacOptions ++= Seq("-deprecation", "-feature", "-target:jvm-1.8")

ThisBuild / javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

ThisBuild / resolvers ++= Seq(
  "Softnetwork Server" at "https://softnetwork.jfrog.io/artifactory/releases/",
  "Maven Central Server" at "https://repo1.maven.org/maven2",
  "Typesafe Server" at "https://repo.typesafe.com/typesafe/releases"
)

ThisBuild / versionScheme := Some("early-semver")

val scalatest = Seq(
  "org.scalatest" %% "scalatest" % Versions.scalatest  % Test
)

ThisBuild / libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1",
  "app.softnetwork.api" %% "generic-server-api" % Versions.server,
  "app.softnetwork.api" %% "generic-server-api-testkit" % Versions.server % Test,
  "app.softnetwork.protobuf" %% "scalapb-extensions" % "0.1.6"
) ++ scalatest

Compile / unmanagedResourceDirectories += baseDirectory.value / "src/main/protobuf"

Test / parallelExecution := false

lazy val root = project.in(file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings, BuildInfoSettings.settings)
  .enablePlugins(AkkaGrpcPlugin, BuildInfoPlugin)
