package com.htmlism

object Multiset {
  def apply[T](elements: (T, Int)*) = new Multiset(Map(elements: _*))

  def apply[T](elements: Map[T, Int]) = new Multiset(elements)
}

class Multiset[T](elementCounts: Map[T, Int]) {
  private val mappable = elementCounts.filter { case (k, count) => count > 0 }

  def apply(element: T) = if (mappable.contains(element)) mappable(element) else 0

  def size = mappable.values.sum

  def isEmpty = size == 0

  def contains(element: T) = mappable.contains(element)

  def toList = mappable.keys.toList.flatMap { k =>
    List.fill(mappable(k))(k)
  }

  def elements = mappable.keys

  def withMaximum(maximum: Int) = Multiset(mappable.map { case (k, n) => (k, if (n > maximum) maximum else n) })

  def without(element: T) = Multiset(mappable - element)

  def +(element: T) = {
    val count = if (mappable.contains(element)) mappable(element) + 1 else 1

    Multiset(mappable + (element -> count))
  }

  override def hashCode() = mappable.hashCode()

  override def equals(that: Any) = that match {
    case set: Multiset[T] => this.mappable == set.mappable
    case _ => false
  }

  override def toString = s"Multiset(${mappable.toString()})"
}
