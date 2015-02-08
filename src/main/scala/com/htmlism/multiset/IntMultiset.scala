package com.htmlism.multiset

import scala.collection.generic.GenericCompanion
import scala.collection.mutable

object IntMultiset extends GenericCompanion[IntMultiset] {
  def newBuilder[A] = new IntMultisetBuilder[A]
}

class IntMultiset[A] extends MultisetNew[A] {
  def apply(v1: A) = ???

  def iterator = ???
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

  def result() = { println(counts); null }

  def clear() = counts.clear()
}
