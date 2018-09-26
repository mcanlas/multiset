package com.htmlism.multiset

import org.specs2.mutable.Specification

class MultisetSpec extends Specification {
  "A multiset" should {
    "not equal some unrelated type" in {
      Multiset() != 'apple
    }
  }
}

class MapMultisetSpec extends Specification {
  // exists for code coverage of concrete class canbuildform
  MapMultiset() ++ MapMultiset()

  // compile time assertion that can build from bridges properly
  MapMultiset('foo).map(identity): MapMultiset[Symbol]

  "A map-backed multiset" should {
    "prevent integer overflow" in {
      {
        MapMultiset() + ('apple, Int.MaxValue) + 'apple
      } must throwA[ArithmeticException]
    }
  }
}

class SeqMultisetSpec extends Specification {
  // exists for code coverage of concrete class canbuildform
  SeqMultiset() ++ SeqMultiset()

  // compile time assertion that can build from bridges properly
  SeqMultiset('foo).map(identity): SeqMultiset[Symbol]
}
