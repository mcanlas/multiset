package com.htmlism

object Multiset {
  /**
   * Creates a multiset with the specified elements.
   */
  def apply[T](elements: (T, Int)*) = new Multiset(Map(elements: _*))

  /**
   * Creates a multiset with the specified elements.
   */
  def apply[T](elements: Map[T, Int]) = new Multiset(elements)

  /**
   * Creates a multiset with the specified element counts.
   */
  def withCounts[T](elements: (T, Int)*) = new Multiset(Map(elements: _*))

  /**
   * An empty multiset of type T.
   */
  def empty[T]: Multiset[T] = Multiset()
}

class Multiset[T](elementCounts: Map[T, Int]) extends Iterable[T] {
  private val mappable = elementCounts.filter { case (k, count) => count > 0 }

  def iterator = elementCounts.iterator.flatMap { case (k, n)  =>
    List.fill(n)(k)
  }

  def apply(element: T) = if (mappable.contains(element)) mappable(element) else 0

  def contains(element: T) = mappable.contains(element)

  def elements = mappable.keys

  def withMaximum(maximum: Int) = new Multiset(mappable.map { case (k, n) => (k, if (n > maximum) maximum else n) })

  def without(element: T) = new Multiset(mappable - element)

  def +(element: T) = {
    val count = if (mappable.contains(element)) mappable(element) + 1 else 1

    new Multiset(mappable + (element -> count))
  }

  def ++(that: Multiset[T]) = {
    val keys = (elements ++ that.elements).toList // relaxing the underlying uniqueness constraint for "performance"

    val pairs = for (k <- keys) yield {
      k -> (this(k) + that(k))
    }

    new Multiset(pairs.toMap)
  }

  def combinations(n: Int) = new MultisetCombinationIterator(this, n)

  override def hashCode() = mappable.hashCode()

  override def equals(that: Any) = that match {
    case set: Multiset[T] => this.mappable == set.mappable
    case _ => false
  }

  override def toString = s"Multiset(${mappable.toString()})"
}
