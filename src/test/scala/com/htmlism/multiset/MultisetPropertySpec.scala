package com.htmlism.multiset

import org.scalacheck.Properties

class MultisetPropertySpec extends Properties("Multiset") {
  import org.scalacheck.Prop.forAll

  property("sequence construction") = forAll { (elements: Seq[Int]) =>
    Multiset(elements: _*).size == elements.size
  }
}
