package org.ps
package util

import model.ResponseModel

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
 * Provides marshaller and unmarshaller for the response
 */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val responseModelJsonFormat: RootJsonFormat[ResponseModel] = jsonFormat3(ResponseModel)
}
