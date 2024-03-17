import app.softnetwork.*

/////////////////////////////////
// Defaults
/////////////////////////////////

ThisBuild / organization := "app.softnetwork"

name := "generic-sequence-api"

ThisBuild / version := "0.4.0"

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
  "app.softnetwork.api" %% "generic-server-api" % Versions.genericPersistence,
  "app.softnetwork.api" %% "generic-server-api-testkit" % Versions.genericPersistence % Test,
  "app.softnetwork.protobuf" %% "scalapb-extensions" % "0.1.7"
) ++ scalatest

Compile / unmanagedResourceDirectories += baseDirectory.value / "src/main/protobuf"

Test / parallelExecution := false

lazy val root = project.in(file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings, app.softnetwork.Info.infoSettings)
  .enablePlugins(AkkaGrpcPlugin, BuildInfoPlugin)
