package com.htmlism.multiset

import scala.collection.generic._
import scala.language.higherKinds

/**
  * A trait for factories of multisets.
  *
  * {{{
  *   val cities = Multiset.withCounts('NewYork -> 3, 'SanFrancisco -> 7, 'Tokyo -> 4)
  * }}}
  *
  * @tparam CC The specific type of multiset that this factory constructs
  */
trait MultisetFactory[CC[X] <: Multiset[X] with GenericTraversableTemplate[X, CC]] extends TraversableFactory[CC] {
  type CanBuildMultiset[A] = CanBuildFrom[CC[_], A, CC[A]]

  /**
    * Creates a multiset with the specified element counts.
    *
    * @tparam A The type of the multiset's elements
    * @param elements A collection of elements paired with their counts
    */
  def fromCounts[A](elements: (A, Int)*): Multiset[A] = {
    val b = newBuilder[A]

    for ((e, n) <- elements)
      for (_ <- 1 to n)
        b += e

    b.result()
  }
}
