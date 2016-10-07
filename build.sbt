lazy val commonSettings = Seq(
  name := "spraytest",
  organization := "com.grburst",
  version := "0.0.1",
  scalaVersion := "2.11.8")

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(

    javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
    scalacOptions ++= (
      "-encoding" :: "UTF-8" ::
      "-unchecked" ::
      "-deprecation" ::
      "-explaintypes" ::
      "-feature" ::
      "-language:_" ::
      "-Xlint:_" ::
      "-Ywarn-unused" ::
      Nil),

    libraryDependencies ++= Seq(
      "io.spray" %% "spray-client" % "1.3.4",
      "io.spray" %% "spray-json" % "1.3.2",
      "com.typesafe.akka" %% "akka-actor" % "2.3.9"),

    initialCommands in console := """
import com.grburst.spraytest
import com.grburst.spraytest._

import scala.concurrent.{Future, Await, ExecutionContext }
import scala.concurrent.duration._
import scala.util.{ Try, Success, Failure }

import spray.client.pipelining._
import spray.http.{ FormData, HttpCookie, HttpRequest, HttpResponse }
import spray.http.Uri
import spray.http.HttpHeaders.{ Cookie, `Set-Cookie` }
import spray.httpx.encoding.Gzip

import akka.actor.ActorSystem
""")
