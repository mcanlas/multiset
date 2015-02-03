package com.htmlism.multiset

import org.scalacheck.Properties

class MultisetProperties extends Properties("Multiset") {
  type A = Char

  import org.scalacheck.Prop.{ forAll, BooleanOperators }

  property("sequence construction") = forAll { (elements: Seq[A]) =>
    Multiset(elements: _*).size == elements.size
  }

  property("count construction") = forAll { counts: Seq[(A, Int)] =>
    Multiset.withCounts(counts: _*).size == counts.toMap.values.filter(_ > 0).sum
  }

  property("iteration") = forAll { (elements: Seq[A]) =>
    Multiset(elements: _*).iterator.toList.sorted == elements.sorted
  }

  property("containment") = forAll { (value: A) =>
    Multiset()(value) == 0 && Multiset(value)(value) == 1
  }

  property("uniqueness") = forAll { (elements: Seq[A]) =>
    Multiset(elements: _*).elements == elements.toSet
  }

  property("maximum") = forAll { (counts: Seq[(A, Int)], maximum: Int) =>
    (maximum >= 0 && maximum < (Int.MaxValue / 100)) ==>
      (Multiset.withCounts(counts: _*).withMaximum(maximum).size <= counts.toMap.count(c => c._2 > 0) * maximum)
  }

  property("without") = forAll { (element: A, count: Int) =>
    val emptyWithout = Multiset().without(element) == Multiset()
    val withWithout  = Multiset.withCounts(element -> count).without(element) == Multiset()

    emptyWithout && withWithout
  }

  property("union") = forAll { (firstElement: A, secondElement: A, count: Int) =>
    val firstSet  = Multiset.withCounts(firstElement -> count)
    val secondSet = Multiset.withCounts(firstElement -> count, secondElement -> count)

    val union = firstSet ++ secondSet

    val effectiveCount = if (count < 0) 0 else count

    (count < Int.MaxValue / 2) ==>
      (union(firstElement) == effectiveCount * 2) &&
        (union(secondElement) == effectiveCount)
  }

  property("combinations") = forAll { (elements: Seq[A], n: Int) =>
    val combinations = Multiset(elements: _*).combinations(n)

    if (n > elements.length || n < 0)
      combinations.isEmpty
    else
      combinations.nonEmpty
  }

  property("to string") = forAll { someElements: Seq[A] =>
    val set = Multiset(someElements: _*)
    val string = set.toString()

    val containsAll = set.elements.foldLeft(true) { (acc, e) =>
      val n = set(e)
      val contains = string.contains(s"$e -> $n")

      acc && contains
    }

    val commaOk =
      if (set.elements.size < 2)
        true
      else
        ", ".r.findAllIn(string).length == set.elements.size - 1

    ("starts with" |: string.startsWith("Multiset(")) &&
      ("ends with" |: string.endsWith(")")) &&
      ("contains"  |: containsAll) &&
      ("commas"    |: commaOk)
  }
}
