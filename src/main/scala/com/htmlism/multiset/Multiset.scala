package com.htmlism.multiset

object Multiset {
  /**
   * Creates a multiset with the specified elements.
   */
  def apply[T](elements: T*) = elements.foldLeft(Multiset.empty[T])((m, e) => m + e)

  /**
   * Creates a multiset with the specified element counts.
   */
  def withCounts[T](elements: (T, Int)*) = new Multiset(Map(elements: _*))

  /**
   * An empty multiset of type T.
   */
  def empty[T]: Multiset[T] = new Multiset(Map.empty[T, Int])
}

class Multiset[T](elementCounts: Map[T, Int])
  extends Iterable[T]
  with Function[T, Int] {
  private val mappable = elementCounts.filter { case (k, count) => count > 0 }

  def iterator = elementCounts.iterator.flatMap { case (k, n)  =>
    List.fill(n)(k)
  }

  def apply(element: T): Int = if (mappable.contains(element)) mappable(element) else 0

  def contains(element: T): Boolean = mappable.contains(element)

  def elements: Iterable[T] = mappable.keys

  def withMaximum(maximum: Int): Multiset[T] = new Multiset(mappable.map { case (k, n) => (k, if (n > maximum) maximum else n) })

  def without(element: T): Multiset[T] = new Multiset(mappable - element)

  def +(element: T): Multiset[T] = {
    val count = if (mappable.contains(element)) mappable(element) + 1 else 1

    new Multiset(mappable + (element -> count))
  }

  def ++(that: Multiset[T]): Multiset[T] = {
    val keys = (elements ++ that.elements).toList // relaxing the underlying uniqueness constraint for "performance"

    val pairs = for (k <- keys) yield {
      k -> (this(k) + that(k))
    }

    new Multiset(pairs.toMap)
  }

  def combinations(n: Int): MultisetCombinationIterator[T] = new MultisetCombinationIterator(this, n)

  override def hashCode() = mappable.hashCode()

  override def equals(that: Any) = that match {
    case set: Multiset[T] => this.mappable == set.mappable
    case _ => false
  }
}
