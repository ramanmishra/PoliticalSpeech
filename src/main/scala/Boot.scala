package org.ps

import actor.AppSupervisorActor
import constants.MiscConstants._
import service.EvaluationService

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.util.Timeout

import java.util.concurrent.TimeUnit

/**
 * Application starting point
 */
object Boot extends App {
  implicit val system: ActorSystem = ActorSystem(SYSTEM)
  implicit val timeout: Timeout = Timeout(20, TimeUnit.SECONDS)
  val supervisorActor = system.actorOf(AppSupervisorActor.props())
  val route = EvaluationService(supervisorActor)
  val bindingFuture = Http().newServerAt(HOST, PORT).bind(route)


  println(s"Server now online. Please navigate to http://localhost:8080/\n")
}
