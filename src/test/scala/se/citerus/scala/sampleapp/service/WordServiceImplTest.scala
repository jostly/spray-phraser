package se.citerus.scala.sampleapp.service

import org.scalatest._
import org.scalatest.concurrent._

class WordServiceImplTest extends FunSuite with Matchers with Eventually {
  val service = new WordServiceImpl {
    override val adjectiveFileName = "/3_adjectives.txt"
    override val animalFileName = "/3_animals.txt"
  }

  test("loading words from a file") {
    val animals = service.loadResource("/3_animals.txt")
    animals shouldBe List("alpaca", "bear", "cheetah")
  }

  test("loading words will filter out single-character headings") {
    val adjectives = service.loadResource("/3_adjectives.txt")
    adjectives shouldBe List("angry", "able", "cheating")
  }

  test("picking a random adjective") {
    val adjectives = (for (i <- 1 to 1000) yield service.randomAdjective().get).toSet
    adjectives should contain allOf("angry", "able", "cheating")
  }

  test("picking a random animal") {
    val animals = (for (i <- 1 to 1000) yield service.randomAnimal().get).toSet
    animals should contain allOf("alpaca", "bear", "cheetah")
  }

  test("picking a random adjective on a specific letter") {
    val adjectives = (for (i <- 1 to 1000) yield service.randomAdjective(Some('a')).get).toSet
    adjectives should contain allOf("angry", "able")
    adjectives should not contain "cheating"
  }

  test("picking a random adjective on a specific letter that does not exist returns None") {
    service.randomAdjective(Some('x')) shouldBe None
  }
}
