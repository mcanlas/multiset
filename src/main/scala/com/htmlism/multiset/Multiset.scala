package com.htmlism.multiset

import scala.collection.generic.{GenericCompanion, GenericTraversableTemplate}

/**
  * A factory for multisets. This object extends Scala's collections framework and thus supports common construction
  * patterns.
  *
  * {{{
  * val cities = Multiset('NewYork, 'NewYork, 'London)
  *
  * val empty = Multiset.empty
  * }}}
  *
  * The default concrete implementation of a multiset is `MapMultiset`.
  */
object Multiset extends MultisetFactory[Multiset] {
  implicit def canBuildFrom[A]: CanBuildMultiset[A] = new GenericCanBuildFrom[A]

  def newBuilder[A]: MapMultisetBuilder[A] = new MapMultisetBuilder[A]
}

/**
  * A base trait for multisets.
  *
  * A multiset is an unordered collection of elements. Unlike Scala's `Set`, each member also has a multiplicity or count.
  * Thus, a given element can exist `n` times in a multiset.
  *
  * This framework provides two concrete implementations of `Multiset`: `MapMultiset` and `SeqMultiset`. The first is backed by a map,
  * representing element counts by integers. The second is backed by a sequence, where an element's count
  * is tied to occurrences of that value in the underlying sequence.
  *
  * `SeqMultiset` is contrived; it serves as both a proof of concept for alternative `Multiset` implementations and an
  * opportunity to preserve iteration order.
  *
  * Because `Multiset` extends `Iterable`, values gain all of the extensibility in Scala's collections library.
  *
  * @tparam A The type of each member of the set
  */
trait Multiset[A]
    extends Function[A, Int]
    with Iterable[A]
    with GenericTraversableTemplate[A, Multiset]
    with MultisetLike[A, Multiset[A]] {

  /**
    * Returns the multiplicity of a given member.
    *
    * @param element A possible member of this set
    * @return Returns the multiplicity of the element is a member of this set, zero otherwise.
    */
  def apply(element: A): Int = count(element)

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
  def combinations(n: Int): MultisetCombinationIterator[A] =
    new MultisetCombinationIterator(this, n)

  override def companion: GenericCompanion[Multiset] = Multiset

  override def equals(that: Any): Boolean =
    that match {
      case thatSet: Multiset[A] => countMap(this) == countMap(thatSet)
      case _                    => false
    }

  override def hashCode: Int = countMap(this).hashCode()

  private[this] def countMap(set: Multiset[A]) = {
    val counts = collection.mutable.Map[A, Int]().withDefaultValue(0)

    for (e <- set)
      counts.update(e, counts(e) + 1)

    counts
  }
}
