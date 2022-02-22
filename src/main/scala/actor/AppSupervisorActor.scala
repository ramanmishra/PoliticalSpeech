package org.ps
package actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

/**
 * This class provides supervisor actor for child actors
 *
 */
class AppSupervisorActor() extends Actor with ActorLogging {
  val requestProcessorActor: ActorRef = context.actorOf(RequestProcessorActor.props())

  context.watch(requestProcessorActor)

  override def receive: Receive = {
    case url: String =>
      requestProcessorActor forward url

    case urls: List[String] =>
      requestProcessorActor forward urls
  }
}


object AppSupervisorActor {
  def props(): Props = Props(new AppSupervisorActor())
}