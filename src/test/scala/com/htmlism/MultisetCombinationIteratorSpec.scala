package com.htmlism

import org.specs2.mutable.Specification

object MultisetCombinationIteratorSpec extends Specification {
  def multi(elements: Int*) = {
    var set = Multiset.empty[Int]

    for (e <- elements) set = set + e

    set
  }

  val emptySet = multi()

  "An empty library" should {
    "yield no decks" in {
      new MultisetCombinationIterator(emptySet, 0).size === 0
      new MultisetCombinationIterator(emptySet, 1).size === 0
      new MultisetCombinationIterator(emptySet, 3).size === 0
    }
  }
}
