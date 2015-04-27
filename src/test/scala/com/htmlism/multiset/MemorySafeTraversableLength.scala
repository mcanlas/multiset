package com.htmlism.multiset

import org.scalacheck.{ Gen, Arbitrary }

object MemorySafeTraversableLength {
  // the run-time of SeqMultiset tests is proportional with this value
  val MAXIMUM_LENGTH = 1000

  implicit def arbitraryLowCount: Arbitrary[MemorySafeTraversableLength] =
    Arbitrary {
      Gen.frequency(1 -> Gen.chooseNum(Int.MinValue, MAXIMUM_LENGTH), 1-> Gen.negNum[Int]).map(MemorySafeTraversableLength.apply)
    }
}

case class MemorySafeTraversableLength(count: Int) extends AnyVal

object PostiiveMemorySafeTraversableLength {
  implicit def arbitraryLowPositiveCount: Arbitrary[PostiiveMemorySafeTraversableLength] =
    Arbitrary {
      Gen.frequency(1 -> Gen.chooseNum(1, MemorySafeTraversableLength.MAXIMUM_LENGTH), 1-> Gen.negNum[Int]).map(PostiiveMemorySafeTraversableLength.apply)
    }
}

case class PostiiveMemorySafeTraversableLength(count: Int) extends AnyVal
