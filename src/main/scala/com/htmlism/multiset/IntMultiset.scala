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
  def +=(elem: A) = ???

  def result() = ???

  def clear() = ???
}
