package com.htmlism.multiset

import scala.collection.generic.GenericCompanion
import scala.collection.mutable

object IntMultiset extends GenericCompanion[IntMultiset] {
  def newBuilder[A] = new IntMultisetBuilder[A]
}

class IntMultiset[A](elementCounts: collection.Map[A, Int]) extends MultisetNew[A] {
  def apply(v1: A) = ???

  def iterator = elementCounts.iterator.flatMap { case (k, n)  =>
    new Iterator[A] {
      var remaining = n

      def hasNext = remaining > 0

      def next() = { remaining -= 1; k }
    }
  }
}

class IntMultisetBuilder[A] extends mutable.Builder[A, IntMultiset[A]] {
  private val counts = mutable.Map[A, Int]()

  def +=(elem: A) = {
    val count =
      if (counts.contains(elem))
        counts(elem)
      else
        0

    counts.update(elem, count + 1)

    this
  }

  def result() = new IntMultiset(counts)

  def clear() = counts.clear()
}
