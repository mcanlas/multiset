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
}
