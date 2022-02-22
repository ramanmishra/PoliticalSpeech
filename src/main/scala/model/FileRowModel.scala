package org.ps
package model

/**
 * Data model to store csv row
 *
 * @param speaker   name of the speaker
 * @param topic     the topic on which speaker is speaking
 * @param date      on which date speaker has spoken
 * @param wordCount number of words he said
 */
case class FileRowModel(
                         speaker: String,
                         topic: String,
                         date: String,
                         wordCount: BigInt)

