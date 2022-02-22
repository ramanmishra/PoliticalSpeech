package org.ps
package service

import model.ResponseModel
import util.JsonSupport

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern._
import akka.util.Timeout

import scala.util.{Failure, Success}

/**
 * This service provides all the endpoints
 *
 * @param supervisorActor supervisor actor of the application
 * @param timeout         ask timeout
 * @param actorSystem     actor system in which actor is being created
 */
class EvaluationService(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                                   actorSystem: ActorSystem) extends JsonSupport {
  val evaluationServiceRoutes: Route =
    concat(
      path("evaluation") {
        get {
          parameter("url".repeated) { urls =>
            urls.toList match {
              case Nil => complete(s"Please provide url to the .csv file")

              case url :: Nil =>
                onComplete((supervisorActor ? url).mapTo[ResponseModel]) {
                  case Success(value: ResponseModel) => complete(value)

                  case Failure(_) => complete("Something Went Wrong")
                }

              case multiple =>
                onComplete((supervisorActor ? multiple).mapTo[ResponseModel]) {
                  case Success(value) => complete(value)

                  case Failure(_) => complete("Something Went Wrong")
                }
            }
          }
        }
      }
    )
}

object EvaluationService {
  def apply(supervisorActor: ActorRef)(implicit timeout: Timeout,
                                       actorSystem: ActorSystem): Route = new EvaluationService(supervisorActor).evaluationServiceRoutes
}