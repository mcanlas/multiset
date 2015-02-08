package com.htmlism.multiset

import scala.collection.generic.GenericCompanion

object IntMultiset extends GenericCompanion[IntMultiset] {
  def newBuilder[A] = ???
}

class IntMultiset[A] extends MultisetNew[A] {
  def apply(v1: A) = ???

  def iterator = ???
}
