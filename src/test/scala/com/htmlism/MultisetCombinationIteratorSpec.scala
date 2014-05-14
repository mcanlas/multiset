package com.htmlism

import org.specs2.mutable.Specification

object MultisetCombinationIteratorSpec extends Specification {
  def multi(elements: Int*) = {
    var set = Multiset.empty[Int]

    for (e <- elements) set = set + e

    set
  }

  val emptySet = multi()
  val someSet  = multi(5, 4, 7)

  "An empty library" should {
    "yield no decks" in {
      new MultisetCombinationIterator(emptySet, 1).size === 0
      new MultisetCombinationIterator(emptySet, 3).size === 0
    }
  }

  "An iterator with chosen size smaller than the required size" should {
    "yield no decks" in {
      new MultisetCombinationIterator(emptySet, 5, someSet).size === 0
    }
  }

  "An iterator with chosen size equal to the required size" should {
    "yield the required set" in {
      new MultisetCombinationIterator(emptySet, 0).toList === emptySet :: Nil
      new MultisetCombinationIterator(emptySet, someSet.size, someSet).toList === someSet :: Nil
    }
  }
}
