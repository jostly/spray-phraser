package se.citerus.scala.sampleapp.service

import scala.util.Random

trait WordServiceImpl extends WordService {

  val adjectiveFileName: String
  val animalFileName: String

  def loadResource(name: String) =
    io.Source.fromInputStream(getClass.getResourceAsStream(name))
      .getLines
      .filterNot(l => l.matches("[A-Z]"))
      .toVector

  lazy val adjectives = loadResource(adjectiveFileName)
  lazy val animals = loadResource(animalFileName)

  private def pick(from: Vector[String], startingWith: Option[Char]): Option[String] = {
    val words = startingWith match {
      case Some(c) => from.filter(word => word.startsWith(c.toString))
      case None => from
    }
    if (words.isEmpty) None
    else Some(words(Random.nextInt(words.size)))
  }

  override def randomAdjective(startingWith: Option[Char] = None): Option[String] =
    pick(adjectives, startingWith)

  override def randomAnimal(startingWith: Option[Char] = None): Option[String] =
    pick(animals, startingWith)

}
