package com.htmlism.multiset

import org.scalacheck.{ Arbitrary, Gen }

object MemorySafeTraversableLength {
  // the run-time of SeqMultiset tests is proportional with this value
  val MAXIMUM_LENGTH = 1000

  implicit def arbitraryLowCount: Arbitrary[MemorySafeTraversableLength] =
    Arbitrary {
      Gen.chooseNum(Int.MinValue, MAXIMUM_LENGTH)
        .map(MemorySafeTraversableLength.apply)
    }
}

case class MemorySafeTraversableLength(count: Int) extends AnyVal

object PositiveMemorySafeTraversableLength {
  implicit def arbitraryLowPositiveCount: Arbitrary[PositiveMemorySafeTraversableLength] =
    Arbitrary {
      Gen.chooseNum(1, MemorySafeTraversableLength.MAXIMUM_LENGTH)
        .map(PositiveMemorySafeTraversableLength.apply)
    }
}

case class PositiveMemorySafeTraversableLength(count: Int) extends AnyVal
