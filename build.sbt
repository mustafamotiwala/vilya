import sbt._

import Keys._

import spray.revolver.RevolverPlugin._

Revolver.settings

name := "Vilya Framework"

organization := "com.entity5.phonex"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

val ScalatraVersion = "2.3.0"

val phantomVersion = "1.2.7"

resolvers += "Websudos releases" at "http://maven.websudos.co.uk/ext-release-local"

resolvers += "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra"          % ScalatraVersion,
  "org.scalatra" %% "scalatra-json"     % ScalatraVersion,
  "org.scalatra" %% "scalatra-auth"     % ScalatraVersion,
  "org.scalatra" %% "scalatra-specs2"   % ScalatraVersion % "test",
  "org.json4s"   %% "json4s-jackson"    % "3.2.10",
  "io.undertow"  %  "undertow-core"     % "1.0.1.Final",
  "io.undertow"  %  "undertow-servlet"  % "1.0.1.Final",
  "org.specs2"   %% "specs2"            % "2.4.2"         % "test",
  "ch.qos.logback"                %  "logback-classic"          % "1.1.2",
  "com.typesafe.akka"             %% "akka-actor"               % "2.3.5",
  "com.typesafe.akka"             %% "akka-testkit"             % "2.3.5",
  "com.github.nscala-time"        %% "nscala-time"              % "1.4.0",
  "com.websudos"                  %% "phantom-dsl"              % phantomVersion,
  "com.datastax.cassandra"        %  "cassandra-driver-core"     % "2.1.0"
)

fork in run := true
