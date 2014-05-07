package com.htmlism

import org.specs2.mutable.Specification

object MultisetCombinationIteratorSpec extends Specification {
  type TestIterator = MultisetCombinationIterator[Int]

  def multi(elements: Int*) = {
    var set = Multiset(Map.empty[Int, Int])

    for (e <- elements) set = set + e

    set
  }

  "An empty library" should {
    "yield no decks" in {
      new TestIterator(Multiset.empty, 0).size === 0
      new TestIterator(Multiset.empty, 1).size === 0
    }
  }
}
