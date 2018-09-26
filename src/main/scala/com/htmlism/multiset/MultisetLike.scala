package com.htmlism.multiset

import scala.collection.{IterableLike, mutable}

/**
  * Blah blah blah
  *
  * @tparam A
  * @tparam B
  */
trait MultisetLike[A, +B] extends IterableLike[A, B] {
  self: B =>

  /**
    * Returns the multiplicity of a given member.
    *
    * @param element A possible member of this set
    * @return Returns the multiplicity of the element if it is a member of this set, zero otherwise.
    */
  def count(element: A): Int

  /**
    * Tests if a given element is a member of this set.
    *
    * @param element A possible member of this set
    * @return `true` if the element belongs to this set, `false` otherwise.
    */
  def contains(element: A): Boolean

  /**
    * Returns a unique collection of the elements.
    *
    * {{{
    * scala> Multiset('NewYork, 'London, 'London).elements
    * res0: Set[Symbol] = Set('NewYork, 'London)
    * }}}
    *
    * @return A set
    */
  def elements: Set[A] = toSet

  /**
    * Returns a copy of this multiset with the given element's multiplicity incremented.
    *
    * {{{
    * scala> Multiset('NewYork, 'London) + 'London
    * res0: com.htmlism.multiset.Multiset[Symbol] = MapMultiset('NewYork, 'London, 'London)
    *
    * scala> Multiset('NewYork, 'London) + ('Paris, 2)
    * res1: com.htmlism.multiset.Multiset[Symbol] = MapMultiset('NewYork, 'Paris, 'Paris, 'London)
    * }}}
    *
    * @param element An element to increment
    * @param n The number of times to increment, defaults to 1
    * @return A new multiset
    */
  def +(element: A, n: Int = 1): B = {
    val b = newBuilder

    for (_ <- 1 to n)
      b += element

    b ++= this

    b.result()
  }

  /**
    * Returns a copy of this multiset with the given element's multiplicity decremented.
    *
    * @param element An element to decrement
    * @return A new multiset
    */
  def -(element: A, n: Int = 1): B =
    if (contains(element) && n > 0) {
      val b     = newBuilder
      var skips = n

      for (x <- this)
        if (x == element && skips > 0)
          skips -= 1
        else
          b += x

      b.result()
    } else
      self

  /**
    * Returns a copy of this multiset, excluding the given element.
    *
    * {{{
    * scala> Multiset('NewYork, 'London, 'London) without 'London
    * res0: com.htmlism.multiset.Multiset[Symbol] = MapMultiset('NewYork)
    * }}}
    *
    * @param element An element to exclude
    * @return A new multiset
    */
  def without(element: A): B =
    if (contains(element)) {
      val b = newBuilder

      for (x <- this)
        if (x != element)
          b += x

      b.result()
    } else
      self

  /**
    * Returns a copy of this multiset with an upper limit for each element's multiplicity.
    *
    * {{{
    * scala> Multiset.withCounts('NewYork -> 3, 'London -> 4) withMaximum 2
    * res0: com.htmlism.multiset.Multiset[Symbol] = MapMultiset('NewYork, 'NewYork, 'London, 'London)
    * }}}
    *
    * @param maximum The maximum multiplicity in the new multiset
    * @return A new multiset
    */
  def withMaximum(maximum: Int): B = {
    val b = newBuilder

    if (maximum > 0) {
      val counts = mutable.Map[A, Int]().withDefaultValue(0)

      for (x <- this if counts(x) < maximum) {
        counts += (x -> (counts(x) + 1))
        b += x
      }
    }

    b.result()
  }
}
