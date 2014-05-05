package com.htmlism

import org.specs2.mutable.Specification

object MultisetCombinationIteratorSpec extends Specification {
  def multi(elements: Int*) = {
    var set = Multiset(Map.empty[Int, Int])

    for (e <- elements) set = set + e

    set
  }

  "An empty library" should {
    "yield no decks" in {
      new MultisetCombinationIterator[Int](Multiset.empty, 0).size === 0
    }
  }
}
