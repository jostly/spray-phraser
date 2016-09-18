package se.citerus.scala.sampleapp.infrastructure

import akka.actor.Actor
import se.citerus.scala.sampleapp.resource.NamingResource
import se.citerus.scala.sampleapp.service.WordServiceImpl

class NamingActor extends Actor with NamingResource with WordServiceImpl {

  override val adjectiveFileName = "/adjectives.txt"
  override val animalFileName = "/animals.txt"

  def actorRefFactory = context
  def receive = runRoute(namingRoute)

}




