package com.htmlism.multiset

import org.scalacheck.Properties

class MultisetProperties extends Properties("Multiset") {

  import org.scalacheck.Prop.{ forAll, BooleanOperators }

  property("sequence construction") = forAll { (elements: Seq[Int]) =>
    Multiset(elements: _*).size == elements.size
  }

  property("count construction") = forAll { counts: Seq[(String, Int)] =>
    Multiset.withCounts(counts: _*).size == counts.toMap.values.filter(_ > 0).sum
  }

  property("iteration") = forAll { (elements: Seq[Int]) =>
    Multiset(elements: _*).iterator.toList.sorted == elements.sorted
  }

  property("containment") = forAll { (value: Char) =>
    Multiset()(value) == 0 && Multiset(value)(value) == 1
  }

  property("uniqueness") = forAll { (elements: Seq[Int]) =>
    Multiset(elements: _*).elements == elements.toSet
  }

  property("maximum") = forAll { (counts: Seq[(String, Int)], maximum: Int) =>
    (maximum >= 0 && maximum < (Int.MaxValue / 100)) ==>
      (Multiset.withCounts(counts: _*).withMaximum(maximum).size <= counts.toMap.count(c => c._2 > 0) * maximum)
  }

  property("without") = forAll { (element: String, count: Int) =>
    val emptyWithout = Multiset().without(element) == Multiset()
    val withWithout  = Multiset.withCounts(element -> count).without(element) == Multiset()

    emptyWithout && withWithout
  }

  property("union") = forAll { (firstElement: String, secondElement: String, count: Int) =>
    val firstSet  = Multiset.withCounts(firstElement -> count)
    val secondSet = Multiset.withCounts(firstElement -> count, secondElement -> count)

    val union = firstSet ++ secondSet

    val effectiveCount = if (count < 0) 0 else count

    (count < Int.MaxValue / 2) ==>
      (union(firstElement) == effectiveCount * 2) &&
        (union(secondElement) == effectiveCount)
  }
}
