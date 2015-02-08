package com.htmlism.multiset

import scala.collection.generic.GenericCompanion
import scala.collection.mutable

object SeqMultiset extends GenericCompanion[SeqMultiset] {
  def newBuilder[A] = new mutable.Builder[A, SeqMultiset[A]] {
    private val elements = mutable.ListBuffer[A]()

    def +=(elem: A) = { elements += elem; this }

    def result() = new SeqMultiset(elements)

    def clear() = elements.clear()
  }
}

class SeqMultiset[A](elements: Seq[A]) extends MultisetNew[A] {
  def apply(v1: A) = ???

  def iterator = elements.iterator
}
