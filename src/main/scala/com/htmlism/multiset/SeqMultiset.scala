package com.htmlism.multiset

import scala.collection.generic.GenericCompanion
import scala.collection.mutable

object SeqMultiset extends GenericCompanion[SeqMultiset] {
  def newBuilder[A] = new mutable.Builder[A, SeqMultiset[A]] {
    def +=(elem: A) = ???

    def result() = ???

    def clear() = ???
  }
}

class SeqMultiset[A] extends MultisetNew[A] {
  def apply(v1: A) = ???

  def iterator = ???
}
