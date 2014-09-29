package com.htmlism

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
  }

  "Constructing a multiset with a count less than one" should {
    "yield an empty multiset" in {
      Multiset(sampleElement -> -1) === Multiset()
      Multiset(sampleElement -> 0) === Multiset()
    }
  }

  "A multiset of one thing" should {
    val set = Multiset(sampleElement -> 1)

    "have size one" in {
      set.size === 1
    }

    "contain that thing" in {
      set.contains(sampleElement) must beTrue
    }

    "have a count of one for that thing" in {
      set(sampleElement) === 1
    }
  }

  "A multiset of two and two" should {
    val set = Multiset('apple -> 2, 'orange -> 3)

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
      val moreFruits = Multiset('strawberry -> 1, 'apple -> 4)

      set ++ moreFruits === Multiset('apple -> 6, 'strawberry -> 1, 'orange -> 3)
    }
  }

  "As a string, a multiset" should {
    "look like Multiset(Map(1 -> 2, 3 -> 4))" in {
      Multiset(1 -> 2, 3 -> 4).toString === "Multiset(Map(1 -> 2, 3 -> 4))"
    }
  }
}
