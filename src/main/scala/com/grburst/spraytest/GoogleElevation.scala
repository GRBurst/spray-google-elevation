package com.grburst.spraytest

import scala.util.{ Success, Failure }
import scala.concurrent.duration._
import scala.concurrent.{ Future, ExecutionContext }
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.event.Logging
import akka.io.IO
import spray.json.{ JsonFormat, DefaultJsonProtocol }
import spray.can.Http
import spray.httpx.SprayJsonSupport
import spray.client.pipelining._
import spray.util._

case class Elevation(location: Location, elevation: Double)
case class Location(lat: Double, lng: Double)
case class GoogleApiResult[T](status: String, results: List[T])

object ElevationJsonProtocol extends DefaultJsonProtocol {
  implicit val locationFormat = jsonFormat2(Location)
  implicit val elevationFormat = jsonFormat2(Elevation)
  implicit def googleApiResultFormat[T: JsonFormat] = jsonFormat2(GoogleApiResult.apply[T])
}

case class GoogleElevation(implicit val system: ActorSystem, implicit val ec: ExecutionContext) {
  val log = Logging(system, getClass)

  def getElevation(lat: Double, long: Double): Future[String] = {

    log.info("Requesting the elevation of Mt. Everest from Googles Elevation API...")

    import ElevationJsonProtocol._
    import SprayJsonSupport._
    val pipeline = sendReceive ~> unmarshal[GoogleApiResult[Elevation]]

    val responseFuture = pipeline {
      Get(s"http://maps.googleapis.com/maps/api/elevation/json?locations=$lat,$long&sensor=false")
    }

    responseFuture map (e => e.results.head.elevation.toString)

  }

  def getMtEverestElevation() = {
    getElevation(27.988056, 86.925278)
  }

  def shutdown(): Unit = {
    IO(Http).ask(Http.CloseAll)(1.second).await
    system.shutdown()
  }
}
