package com.htmlism

class MultisetCombinationIterator[A](givenSet: Multiset[A], choose: Int, accumulator: Multiset[A] = Multiset.empty[A]) extends Iterator[Multiset[A]] {
  private var remainingSet = givenSet withMaximum choose - accumulator.size
  private var currentElement = Option.empty[A] // TODO initial condition is clunky
  private var count = 0
  private var subIterator = Option.empty[MultisetCombinationIterator[A]]

  var hasNext = accumulator.size <= choose && remainingSet.size + accumulator.size >= choose

  if (remainingSet.size + accumulator.size > choose && accumulator.size < choose)
    shiftAndReload()

  def next(): Multiset[A] = if (hasNext) {
    subIterator match {
      case Some(iterator) =>
        if (iterator.hasNext) {
          iterator.next()
        } else {
          if (count > 1) {
            count = count - 1

            reloadSubIterator()
          } else {
            shiftAndReload()
          }

          assert(count + remainingSet.size + accumulator.size >= choose)

          val next = subIterator.get.next()
          hasNext = subIterator.get.hasNext || count + remainingSet.size + accumulator.size > choose

          next
        }
      case None =>
        if (accumulator.size == choose) {
          hasNext = false

          accumulator
        } else if (remainingSet.size + accumulator.size == choose) {
          hasNext = false

          accumulator ++ remainingSet
        } else {
          throw new RuntimeException("could not generate a new set for the base case")
        }
    }
  } else {
    throw new UnsupportedOperationException("cannot generate a new set with an empty iterator")
  }

  private def shiftAndReload() = {
    currentElement = Some(remainingSet.elements.toList.head)
    count = remainingSet(currentElement.get)

    remainingSet = remainingSet without currentElement.get

    reloadSubIterator()
  }

  private def reloadSubIterator() = {
    subIterator = Some(new MultisetCombinationIterator(remainingSet, choose, accumulator ++ Multiset(currentElement.get -> count)))
  }
}
