package org.ps
package actor

import constants.MiscConstants.EMPTY
import model.ResponseModel

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern._

import java.io.{File, FileWriter}
import java.net.URL
import java.sql.Timestamp
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import scala.sys.process._
import scala.util.{Failure, Success, Try}

/**
 * This actor takes care of all the processing requests
 *
 */
class RequestProcessorActor() extends Actor with FileProcessor with ActorLogging {

  def receive: Receive = {
    case url: String =>
      val fileName = new Timestamp(System.currentTimeMillis()).toLocalDateTime
      Try {
        new URL(url)
      } match {
        case Failure(_) => Future(ResponseModel(EMPTY, EMPTY, EMPTY)) pipeTo sender()

        case Success(value) => value #> new File(s"./${fileName.toString}") !!
      }

      Future(processFile(fileName.toString)) pipeTo sender()

    case urls: List[String] =>
      val fileName = new Timestamp(System.currentTimeMillis()).toLocalDateTime
      Try {
        new URL(urls.head)
      } match {
        case Failure(_) => Future(ResponseModel(EMPTY, EMPTY, EMPTY)) pipeTo sender()

        case Success(value) => value #> new File(s"./${fileName.toString}") !!
      }


      urls.drop(1).foreach { url =>
        val fileUrl = Source.fromURL(url)
        val content: String = s"\n${fileUrl.getLines().drop(1).mkString("\n")}"

        val out = new FileWriter(new File(fileName.toString), true)

        Try {
          out.write(content)
        } match {
          case _ => out.close()
        }
      }

      val result = processFile(fileName.toString)
      Future(result) pipeTo sender()
  }
}

object RequestProcessorActor {
  def props(): Props = Props(new RequestProcessorActor())
}