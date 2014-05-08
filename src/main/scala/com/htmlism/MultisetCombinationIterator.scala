package com.htmlism

class MultisetCombinationIterator[A](set: Multiset[A], choose: Int, accumulator: Multiset[A] = Multiset.empty[A]) extends Iterator[Multiset[A]] {
  def hasNext = ???

  def next() = ???
}
