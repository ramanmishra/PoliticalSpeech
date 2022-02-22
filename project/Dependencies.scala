import Versions._
import sbt._

/**
 *
 * This object has all the project dependencies
 */
package object Dependencies {

  object AkkaDeps {
    val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaActorV
    val akkaActorTyped = "com.typesafe.akka" %% "akka-actor-typed" % akkaActorV
    val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpV
    val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaStreamV
    val akkaSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV
  }

  val akkaLibs = Seq(AkkaDeps.akkaActor, AkkaDeps.akkaHttp, AkkaDeps.akkaStream, AkkaDeps.akkaActorTyped, AkkaDeps.akkaSprayJson)

  object JsonDeps {
    val sprayJson = "io.spray" %% "spray-json" % sprayJsonV
  }

  val jsonLibs = Seq(JsonDeps.sprayJson)

  val projectDeps: Seq[ModuleID] = akkaLibs ++ jsonLibs
}
