package com.htmlism.multiset

class MultisetCombinationIterator[A](source: Multiset[A], choose: Int, requiredSet: Multiset[A] = Multiset.empty[A]) extends Iterator[Multiset[A]] {
  private var remainingSet = source withMaximum choose - requiredSet.size
  private var currentElement: A = _
  private var count = 0
  private var subIterator = Option.empty[MultisetCombinationIterator[A]]

  var hasNext = requiredSet.size <= choose && remainingSet.size + requiredSet.size >= choose

  if (remainingSet.size + requiredSet.size > choose && requiredSet.size < choose)
    shiftAndReload()

  def next(): Multiset[A] = if (hasNext) {
    subIterator match {
      case Some(iterator) =>
        if (iterator.hasNext) {
          iterator.next()
        } else {
          if (count > 1) {
            count -= 1

            reloadSubIterator()
          } else {
            shiftAndReload()
          }

          assert(count + remainingSet.size + requiredSet.size >= choose)

          // subIterator must be re-evaluated because it may have been modified since the case match.
          // don't change `subIterator.get` to `iterator`!
          val next = subIterator.get.next()
          hasNext = subIterator.get.hasNext || count + remainingSet.size + requiredSet.size > choose

          next
        }
      case None =>
        if (requiredSet.size == choose) {
          hasNext = false

          requiredSet
        } else if (remainingSet.size + requiredSet.size == choose) {
          hasNext = false

          requiredSet ++ remainingSet
        } else {
          throw new RuntimeException("could not generate a new set for the base case")
        }
    }
  } else {
    throw new UnsupportedOperationException("cannot generate a new set with an empty iterator")
  }

  private def shiftAndReload() = {
    currentElement = remainingSet.elements.toList.head
    count = remainingSet(currentElement)

    remainingSet = remainingSet without currentElement

    reloadSubIterator()
  }

  private def reloadSubIterator() = {
    subIterator = Some(new MultisetCombinationIterator(remainingSet, choose, requiredSet ++ Multiset.withCounts(currentElement -> count)))
  }
}
