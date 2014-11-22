package com.htmlism.multiset

/** This object provides operations that create `Multiset` values. */

object Multiset {
  /**
   * Creates a multiset with the specified elements.
   *
   * @tparam A The type of the multiset's elements
   * @param elements A collection of individual elements
   */
  def apply[A](elements: A*): Multiset[A] = elements.foldLeft(Multiset.empty[A])((m, e) => m + e)

  /**
   * Creates a multiset with the specified element counts.
   *
   * @tparam A The type of the multiset's elements
   * @param elements A collection of elements paired with their counts
   */
  def withCounts[A](elements: (A, Int)*): Multiset[A] = new Multiset(Map(elements: _*))

  /**
   * An empty multiset.
   *
   * @tparam A The type of the multiset's elements
   */
  def empty[A]: Multiset[A] = new Multiset(Map.empty[A, Int])
}

/**
 * A class for immutable multisets, unordered collections of elements.
 *
 * Unlike the sets provided in Scala's standard library, multisets can contain duplicate members.
 * Duplicate members are compactly represented by counts.
 *
 * {{{
 * scala> Multiset('NewYork, 'NewYork, 'London)
 * res0: com.htmlism.multiset.Multiset[Symbol] = Multiset('NewYork -> 2, 'London -> 1)
 *
 * scala> Multiset.withCounts('NewYork -> 3, 'London -> 4)
 * res1: com.htmlism.multiset.Multiset[Symbol] = Multiset('NewYork -> 3, 'London -> 4)
 *
 * scala> Multiset.empty
 * res2: com.htmlism.multiset.Multiset[Nothing] = Multiset()
 * }}}
 *
 * @param elementCounts A collection of elements paired with their counts
 * @tparam A The type of the multiset's elements
 */

class Multiset[A](elementCounts: Map[A, Int])
  extends Iterable[A]
  with Function[A, Int] {
  private val mappable = elementCounts.filter { case (k, count) => count > 0 }

  def iterator = elementCounts.iterator.flatMap { case (k, n)  =>
    List.fill(n)(k)
  }

  /**
   * Returns the number of instances for a given element.
   *
   * @param element The element to look for
   * @return The number of instances
   */

  def apply(element: A): Int = if (mappable.contains(element)) mappable(element) else 0

  /**
   * Tests if a given element exists in the set
   *
   * @param element The element to test
   * @return `true` if the element exists, `false` otherwise
   */

  def contains(element: A): Boolean = mappable.contains(element)

  /**
   * Returns a set of the elements
   *
   * {{{
   * scala> Multiset('NewYork, 'London, 'London).elements
  *  res0: Set[Symbol] = Set('NewYork, 'London)
   * }}}
   *
   * @return A collection of elements
   */

  def elements: Set[A] = mappable.keys.toSet

  /**
   * Returns a new multiset with the counts of each element limited to some maximum
   *
   * {{{
   * scala> Multiset.withCounts('NewYork -> 3, 'London -> 4).withMaximum(2)
   * res0: com.htmlism.multiset.Multiset[Symbol] = Multiset('NewYork -> 2, 'London -> 2)
   * }}}
   *
   * @param maximum The maximum number of instances for each element in the new multiset
   * @return A multiset with the maximum applied
   */

  def withMaximum(maximum: Int): Multiset[A] = new Multiset(mappable.map { case (k, n) => (k, if (n > maximum) maximum else n) })

  /**
   * Returns a new multiset without a given element
   *
   * This method removes all instances of the specified element.
   *
   * {{{
   * scala> Multiset('NewYork, 'London, 'London).without('London)
   * res0: com.htmlism.multiset.Multiset[Symbol] = Multiset('NewYork -> 1)
   * }}}
   *
   * @param element The element to exclude from the new multiset
   * @return A multiset without the specified element
   */

  def without(element: A): Multiset[A] = new Multiset(mappable - element)

  /**
   * Returns a new multiset with a given element added to it
   *
   * If the element already exists, its count increases by one.
   *
   * {{{
   * scala> Multiset('NewYork, 'London) + 'London
   * res0: com.htmlism.multiset.Multiset[Symbol] = Multiset('NewYork -> 1, 'London -> 2)
   * }}}
   *
   * @param element The element to add
   * @return A new multiset including the specified element
   */

  def +(element: A): Multiset[A] = {
    val count = if (mappable.contains(element)) mappable(element) + 1 else 1

    new Multiset(mappable + (element -> count))
  }

  /**
   * Returns the union of this multiset and another
   *
   * If elements exist in both sets, their counts are combined.
   *
   * {{{
   * scala> Multiset('NewYork, 'London) ++ Multiset('London, 'Tokyo)
   * res0: com.htmlism.multiset.Multiset[Symbol] = Multiset('NewYork -> 1, 'London -> 2, 'Tokyo -> 1)
   * }}}
   *
   * @param that The other multiset to combine with
   * @return A new multiset with elements from both sets
   */

  def ++(that: Multiset[A]): Multiset[A] = {
    val keys = elements ++ that.elements

    val pairs = for (k <- keys.view.toSeq) yield {
      k -> (this(k) + that(k))
    }

    new Multiset(pairs.toMap)
  }

  /**
   * Returns an iterator of n-combination multisets
   *
   * {{{
   * scala> Multiset('NewYork, 'NewYork, 'London, 'Tokyo).combinations(2).foreach(println)
   * Multiset('NewYork -> 2)
   * Multiset('NewYork -> 1, 'London -> 1)
   * Multiset('NewYork -> 1, 'Tokyo -> 1)
   * Multiset('London -> 1, 'Tokyo -> 1)
   * }}}
   *
   * @param n The number of elements to choose
   * @return An iterator of multisets
   */

  def combinations(n: Int): MultisetCombinationIterator[A] = new MultisetCombinationIterator(this, n)

  override def toString() = "Multiset(" + elementCounts.map { case (e, n) => s"$e -> $n" }.mkString(", ") + ")"

  override def hashCode() = mappable.hashCode()

  override def equals(that: Any) = that match {
    case set: Multiset[A] => this.mappable == set.mappable
    case _ => false
  }
}
