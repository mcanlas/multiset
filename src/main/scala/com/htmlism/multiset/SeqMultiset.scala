package com.htmlism.multiset

import scala.collection.generic.GenericCompanion

object SeqMultiset extends GenericCompanion[SeqMultiset] {
  def newBuilder[A] = ???
}

class SeqMultiset[A] extends MultisetNew[A] {
  def apply(v1: A) = ???

  def iterator = ???
}
