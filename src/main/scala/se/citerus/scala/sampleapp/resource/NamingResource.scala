package se.citerus.scala.sampleapp.resource

import se.citerus.scala.sampleapp.api.Phrase
import se.citerus.scala.sampleapp.service.WordService
import spray.routing.HttpService

trait NamingResource extends HttpService with WordService {

  val namingRoute =
    rejectEmptyResponse {
      get {
        path("") {
          complete {
            for {
              adjective <- randomAdjective()
              animal <- randomAnimal(Some(adjective.head))
            } yield Phrase(adjective, animal)
          }
        } ~
          path("random") {
            complete {
              for {
                adjective <- randomAdjective()
                animal <- randomAnimal()
              } yield Phrase(adjective, animal)
            }
          } ~
          path("[a-z]".r) { start =>
            complete {
              val first = start.head
              for {
                adjective <- randomAdjective(Some(first))
                animal <- randomAnimal(Some(adjective.head))
              } yield Phrase(adjective, animal)
            }
          }
      }
    }
}
