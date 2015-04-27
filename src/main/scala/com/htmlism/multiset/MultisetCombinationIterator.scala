package com.htmlism.multiset

/**
 * This class represents an iterator of n-combination multisets from a source multiset.
 *
 * {{{
 * scala> Multiset('NewYork, 'NewYork, 'London, 'Tokyo).combinations(2).foreach(println)
 * Multiset('NewYork -> 2)
 * Multiset('NewYork -> 1, 'London -> 1)
 * Multiset('NewYork -> 1, 'Tokyo -> 1)
 * Multiset('London -> 1, 'Tokyo -> 1)
 * }}}
 *
 * @param source The source multiset
 * @param choose The number of elements to choose
 * @param requiredSet A multiset to be included with each combination
 * @tparam A The type of the multiset's elements
 */

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
        hasNext = false

        if (requiredSet.size == choose) {
          requiredSet
        } else {
          // by now, this is the only logical outcome left
          assert(remainingSet.size + requiredSet.size == choose)

          requiredSet ++ remainingSet
        }
    }
  } else {
    throw new UnsupportedOperationException("cannot generate a new set with an empty iterator")
  }

  private def shiftAndReload() = {
    currentElement = remainingSet.elements.head
    count = remainingSet(currentElement)

    remainingSet = remainingSet without currentElement

    reloadSubIterator()
  }

  private def reloadSubIterator() = {
    subIterator = Some(new MultisetCombinationIterator(remainingSet, choose, requiredSet + (currentElement, count)))
  }
}
