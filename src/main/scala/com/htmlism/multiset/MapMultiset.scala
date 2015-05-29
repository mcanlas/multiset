package com.htmlism.multiset

import scala.collection.mutable
import scala.collection.generic.{ GenericCompanion, GenericTraversableTemplate }

/**
 * A factory for multisets backed by maps. This object extends Scala's collections framework and thus supports common construction
 * methods.
 *
 * {{{
 * val cities = MapMultiset('NewYork, 'NewYork, 'London)
 *
 * val empty = MapMultiset.empty
 * }}}
 */

object MapMultiset extends MultisetFactory[MapMultiset] {
  implicit def canBuildFrom[A]: GenericCanBuildFrom[A] = ReusableCBF.asInstanceOf[GenericCanBuildFrom[A]]

  def newBuilder[A] = new MapMultisetBuilder[A]

  /**
   * Creates a map-backed multiset with the specified element counts.
   *
   * This factory method ensures that the given counts are greater than zero.
   *
   * @param counts A map of members to their multiplicity
   * @tparam A The type of the multiset's elements
   */

  def fromCounts[A](counts: collection.Map[A, Int]): MapMultiset[A] = {
    val positiveCounts = counts.filter { case (k, count) => count > 0 }

    new MapMultiset(positiveCounts)
  }
}

/**
 * A multiset backed by a map. The addition of elements to the set will merely increment each element's multiplicity,
 * represented by an `Int`. Thus, this collection scales well as existing elements are added.
 *
 * Accessing the multiplicity of a member is meant to be fast, though its performance will ultimately be tied to the
 * implementation of the backing map. Scala's default map implementation is a hash map, which does provide fast lookup.
 *
 * Iteration order will most likely not match the build order. Equal members will be iterated together.
 *
 * `MapMultiset` has a private constructor. Use the companion object's factory method `fromCounts` to construct instances
 * of `MapMultiset.`
 *
 * @param counts A map of members to their multiplicity
 * @tparam A The type of each member of the set
 */

class MapMultiset[A] private(counts: collection.Map[A, Int])
  extends Multiset[A]
  with GenericTraversableTemplate[A, MapMultiset]
  with MultisetLike[A, MapMultiset[A]]
{
  assert(!counts.exists(_._2 < 1))

  def count(element: A): Int = if (contains(element)) counts(element) else 0

  def contains(element: A) = counts.contains(element)

  def iterator: Iterator[A] = counts.iterator.flatMap { case (k, n)  =>
    new Iterator[A] {
      var remaining = n

      def hasNext = remaining > 0

      def next() = { remaining -= 1; k }
    }
  }

  override val size: Int = counts.values.sum

  override def elements: Set[A] = counts.keys.toSet

  override def +(element: A, n: Int): MapMultiset[A] =
    if (n > 0) {
      val oldCount = count(element)
      val newCount = oldCount + n

      if (newCount <= oldCount)
        throw new ArithmeticException(s"cannot add $n to $oldCount without overflowing")

      new MapMultiset(counts + (element -> newCount))
    } else
      this

  override def -(element: A, n: Int): MapMultiset[A] =
    if (contains(element) && n > 0) {
      val newCount = Math.max(counts(element) - n, 0)

      if (newCount > 0)
        new MapMultiset(counts + (element -> newCount))
      else
        new MapMultiset(counts - element)
    } else
      this

  override def without(element: A): MapMultiset[A] =
    if (contains(element))
      new MapMultiset(counts - element)
    else
      this

  override def withMaximum(maximum: Int): MapMultiset[A] =
    if (maximum < 1)
      MapMultiset.empty
    else {
      val max = counts.map { case (k, n) => k -> (if (n > maximum) maximum else n) }

      new MapMultiset(max)
    }

  override def companion: GenericCompanion[MapMultiset] = MapMultiset

  override def hashCode(): Int = counts.hashCode()
}

/**
 * A builder for multisets backed by a map.
 *
 * @tparam A The type of the members in the multiset
 */

class MapMultisetBuilder[A] extends mutable.Builder[A, MapMultiset[A]] {
  private val counts = mutable.Map[A, Int]().withDefaultValue(0)

  def +=(elem: A) = {
    val count = counts(elem)

    counts.update(elem, count + 1)

    this
  }

  def result() = MapMultiset.fromCounts(counts)

  def clear() = counts.clear()
}
