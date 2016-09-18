package se.citerus.scala.sampleapp.resource

import org.scalatest.concurrent.Eventually
import org.scalatest.{FunSuite, Inside, Matchers}
import se.citerus.scala.sampleapp.service.WordServiceImpl
import spray.http.HttpRequest
import spray.http.StatusCodes._
import spray.testkit.ScalatestRouteTest

class NamingResourceTest extends FunSuite with Matchers with Inside with Eventually
  with ScalatestRouteTest with NamingResource with WordServiceImpl {

  def actorRefFactory = system
  override val adjectiveFileName: String = "/3_adjectives.txt"
  override val animalFileName: String = "/3_animals.txt"
  val PhraseR = "(.+) (.+)".r

  def get(s: String = "/"): HttpRequest = Get(s) ~> addHeader("Accept", "text/plain")

  test("asking for alliterative phrase") {
    get() ~> namingRoute ~> check {
      inside(responseAs[String]) {
        case PhraseR(adj, ani) =>
          List(adj) should contain oneElementOf loadResource(adjectiveFileName)
          List(ani) should contain oneElementOf loadResource(animalFileName)
          ani should startWith (adj.head.toString)
      }
    }
  }

  test("asking for truly random phrase") {
    eventually {
      get("/random") ~> namingRoute ~> check {
        inside(responseAs[String]) {
          case PhraseR(adj, ani) =>
            List(adj) should contain oneElementOf loadResource(adjectiveFileName)
            List(ani) should contain oneElementOf loadResource(animalFileName)
            ani shouldNot startWith (adj.head.toString)
        }
      }
    }
  }

  test("asking for a phrase beginning with one specific letter") {
    get("/c") ~> namingRoute ~> check {
      responseAs[String] shouldBe "cheating cheetah"
    }
  }

  test("asking for a phrase beginning with a letter that has no word") {
    get("/x") ~> sealRoute(namingRoute) ~> check {
      status shouldBe NotFound
    }
  }

}
