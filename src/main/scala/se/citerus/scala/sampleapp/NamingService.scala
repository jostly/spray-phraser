package se.citerus.scala.sampleapp

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._

class NamingActor extends Actor with NamingService with WordServiceImpl {

  override val adjectiveFileName = "/adjectives.txt"
  override val animalFileName = "/animals.txt"

  def actorRefFactory = context
  def receive = runRoute(namingRoute)

}

trait NamingService extends HttpService with WordService {

  val namingRoute =
    rejectEmptyResponse {
      get {
        path("") {
          complete {
            for {
              adjective <- randomAdjective()
              animal <- randomAnimal(Some(adjective.head))
            } yield s"$adjective $animal"
          }
        } ~
        path("random") {
          complete {
            for {
              adjective <- randomAdjective()
              animal <- randomAnimal()
            } yield s"$adjective $animal"
          }
        } ~
        path("[a-z]".r) { start =>
          complete {
            val first = start.head
            for {
              adjective <- randomAdjective(Some(first))
              animal <- randomAnimal(Some(adjective.head))
            } yield s"$adjective $animal"
          }
        }
      }
    }
}

trait WordService {
  def randomAdjective(startingWith: Option[Char] = None): Option[String]
  def randomAnimal(startingWith: Option[Char] = None): Option[String]
}
