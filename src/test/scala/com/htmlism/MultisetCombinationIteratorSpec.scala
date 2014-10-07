package com.htmlism

import org.specs2.mutable.Specification

class MultisetCombinationIteratorSpec extends Specification {
  def multi(elements: Int*) = {
    var set = Multiset.empty[Int]

    for (e <- elements) set = set + e

    set
  }

  private val empty = Multiset.empty[Int]
  val someSet  = multi(5, 4, 7)

  "An empty library" should {
    "yield no decks" in {
      new MultisetCombinationIterator(empty, 1).size === 0
      new MultisetCombinationIterator(empty, 3).size === 0
    }
  }

  "An iterator with a chosen size less than the accumulator size" should {
    "yield no decks" in {
      new MultisetCombinationIterator(empty, someSet.size - 1, someSet).size === 0
    }
  }

  "An iterator with chosen size equal to the required size" should {
    "yield the required set" in {
      new MultisetCombinationIterator(empty, 0).toList === empty :: Nil
      new MultisetCombinationIterator(empty, someSet.size, someSet).toList === someSet :: Nil
    }
  }

  "An iterator with a given set, choose 3" should {
    val bigSet = Multiset.withCounts(4 -> 3, 7 -> 3, 9 -> 3)

    val answers = List(
      multi(4, 4, 4),
      multi(4, 4, 7),
      multi(4, 4, 9),
      multi(4, 7, 7),
      multi(4, 7, 9),
      multi(4, 9, 9),
      multi(7, 7, 7),
      multi(7, 7, 9),
      multi(7, 9, 9),
      multi(9, 9, 9))

    "generate the right stuff" in {
      new MultisetCombinationIterator(bigSet, 3).toSeq must containTheSameElementsAs(answers)
    }
  }

  "An iterator with a given set, choose 4" should {
    val set = multi(4, 4, 7, 7, 3, 3)

    val answers = List(
      multi(4, 4, 7, 7),
      multi(4, 4, 7, 3),
      multi(4, 4, 3, 3),
      multi(4, 7, 7, 3),
      multi(4, 7, 3, 3),
      multi(7, 7, 3, 3))

    "generate the right stuff" in {
      new MultisetCombinationIterator(set, 4).toSeq must containTheSameElementsAs(answers)
    }
  }
}
