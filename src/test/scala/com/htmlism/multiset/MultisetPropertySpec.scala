package com.htmlism.multiset

import org.scalacheck.Properties

class MultisetPropertySpec extends Properties("Multiset") {

  import org.scalacheck.Prop.forAll

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
}
