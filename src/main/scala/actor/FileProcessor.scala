package org.ps
package actor

import constants.MiscConstants._
import model.{FileRowModel, ResponseModel}

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
 * Business logic to process the provided file
 */
trait FileProcessor {
  def processFile(fileName: String): ResponseModel = {
    val source = Source.fromFile(fileName)
    val content = source.getLines().toList.drop(1).filter(_ != EMPTY)

    val fileRows: List[FileRowModel] = content.map { line =>
      val columns = line.split(COMMA).toList
      FileRowModel(columns.head, columns(1), columns(2), BigInt(columns.last.trim))
    }

    val speakerGroup = fileRows.groupBy(_.speaker)

    val mostSpeechYear = fileRows.groupBy(x => (x.speaker, x.date.split(DATE_SEPARATOR).head.trim)).filter(_._1._2 == YEAR)

    val speakerYearMostSpeech: String = Try {
      mostSpeechYear.maxBy(_._2.length)
    } match {
      case Failure(_) => NULL

      case Success(value) => value._1._1
    }

    val leastWord = speakerGroup.view.mapValues(_.map(_.wordCount).sum)

    val speakerLeastWord: String = Try {
      leastWord.minBy(_._2)
    } match {
      case Failure(_) => NULL
      case Success(value) => value._1
    }
    val mostSpeech = fileRows.groupBy(_.topic).view.filter(_._1.trim == TOPIC).mapValues(_.map(_.speaker))

    val speakerMostSpeechTopic: String = Try {
      mostSpeech.maxBy(_._2.length)
    } match {
      case Failure(_) => NULL
      case Success(value) => value._2.head
    }

    ResponseModel(speakerYearMostSpeech, speakerMostSpeechTopic, speakerLeastWord)
  }
}
