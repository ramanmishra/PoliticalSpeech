package org.ps
package model

/**
 * Response model for the request
 *
 * @param mostSpeeches most speech given in 2013
 * @param mostSecurity most speech given on Innere Sicherheit
 * @param leastWordy   lest word spoken by a speaker
 */
case class ResponseModel(
                          mostSpeeches: String,
                          mostSecurity: String,
                          leastWordy: String
                        )
