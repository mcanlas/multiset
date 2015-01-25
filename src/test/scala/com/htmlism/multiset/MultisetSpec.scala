package com.htmlism.multiset

import org.specs2.mutable.Specification

class MultisetSpec extends Specification {
  val sampleElement = 'foo

  "An empty multiset" should {
    val set = Multiset[Symbol]()

    "have a size of zero" in {
      set.size === 0
    }

    "have a count of zero for some element" in {
      set(sampleElement) === 0
    }

    "be equal to itself, like a value class" in {
      set === Multiset()
    }

    "have an empty list of elements" in {
      set.elements === Set()
    }

    "have no combinations of one" in {
      set.combinations(1).size === 0
    }
  }

  "Constructing a multiset with a count less than one" should {
    "yield an empty multiset" in {
      Multiset.withCounts(sampleElement -> -1) === Multiset()
      Multiset.withCounts(sampleElement -> 0) === Multiset()
    }
  }

  "A multiset of one element" should {
    val set = Multiset(sampleElement)

    "have size one" in {
      set.size === 1
    }

    "contain that element" in {
      set.contains(sampleElement) must beTrue
    }

    "have a count of one for that element" in {
      set(sampleElement) === 1
    }

    "act like a function" in {
      List(sampleElement).map(set) === List(1)
    }
  }

  "A multiset of two and two" should {
    val set = Multiset.withCounts('apple -> 2, 'orange -> 3)

    "have size four" in {
      set.size === 5
    }

    "have size two when maxed at 1" in {
      val maximizedSet = set.withMaximum(1)

      maximizedSet.size === 2
      maximizedSet('apple) === 1
      maximizedSet('orange) === 1
    }

    "be empty when maxed at 0" in {
      set.withMaximum(0) === Multiset()
    }

    "have size two, without the second element" in {
      set.without('orange).size === 2
    }

    "support merging" in {
      val moreFruits = Multiset.withCounts('strawberry -> 1, 'apple -> 4)

      // cannot use specs2 more informative === operator because the two ways to the same map yield different iterators
      set ++ moreFruits == Multiset.withCounts('apple -> 6, 'strawberry -> 1, 'orange -> 3)
    }
  }

  "Creating a multiset via the companion object" should {
    "yield the right value" in {
      Multiset('boom, 'boom, 'pow) == new Multiset(Map('boom -> 2, 'pow -> 1))
    }
  }
}
