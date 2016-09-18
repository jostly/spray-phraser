package se.citerus.scala.sampleapp.service

trait WordService {
  def randomAdjective(startingWith: Option[Char] = None): Option[String]
  def randomAnimal(startingWith: Option[Char] = None): Option[String]
}
