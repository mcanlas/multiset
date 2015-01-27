package com.htmlism.multiset

import org.specs2.mutable.Specification

class MultisetCombinationIteratorSpec extends Specification {
  private val empty = Multiset.empty[Int]
  private val someSet = Multiset(5, 4, 7)

  "An empty iterator" should {
    "yield no combinations" in {
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
      Multiset(4, 4, 4),
      Multiset(4, 4, 7),
      Multiset(4, 4, 9),
      Multiset(4, 7, 7),
      Multiset(4, 7, 9),
      Multiset(4, 9, 9),
      Multiset(7, 7, 7),
      Multiset(7, 7, 9),
      Multiset(7, 9, 9),
      Multiset(9, 9, 9))

    "generate the right stuff" in {
      new MultisetCombinationIterator(bigSet, 3).toSeq must containTheSameElementsAs(answers)
    }
  }

  "An iterator with a given set, choose 4" should {
    val set = Multiset(4, 4, 7, 7, 3, 3)

    val answers = List(
      Multiset(4, 4, 7, 7),
      Multiset(4, 4, 7, 3),
      Multiset(4, 4, 3, 3),
      Multiset(4, 7, 7, 3),
      Multiset(4, 7, 3, 3),
      Multiset(7, 7, 3, 3))

    "generate the right stuff" in {
      new MultisetCombinationIterator(set, 4).toSeq must containTheSameElementsAs(answers)
    }
  }
}
