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

  def apply(element: A): Int = if (mappable.contains(element)) mappable(element) else 0

  def contains(element: A): Boolean = mappable.contains(element)

  def elements: Iterable[A] = mappable.keys

  def withMaximum(maximum: Int): Multiset[A] = new Multiset(mappable.map { case (k, n) => (k, if (n > maximum) maximum else n) })

  def without(element: A): Multiset[A] = new Multiset(mappable - element)

  def +(element: A): Multiset[A] = {
    val count = if (mappable.contains(element)) mappable(element) + 1 else 1

    new Multiset(mappable + (element -> count))
  }

  def ++(that: Multiset[A]): Multiset[A] = {
    val keys = elements ++ that.elements

    val pairs = for (k <- keys) yield {
      k -> (this(k) + that(k))
    }

    new Multiset(pairs.toMap)
  }

  def combinations(n: Int): MultisetCombinationIterator[A] = new MultisetCombinationIterator(this, n)

  override def hashCode() = mappable.hashCode()

  override def equals(that: Any) = that match {
    case set: Multiset[A] => this.mappable == set.mappable
    case _ => false
  }
}
