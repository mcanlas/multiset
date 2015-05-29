package com.htmlism.multiset

import scala.collection.mutable
import scala.collection.generic.{ GenericCompanion, GenericTraversableTemplate }

/**
 * A factory for multisets backed by sequences. This object extends Scala's collections framework and thus supports common construction
 * methods.
 *
 * {{{
 * val cities = SeqMultiset('NewYork, 'NewYork, 'London)
 *
 * val empty = SeqMultiset.empty
 * }}}
 */

object SeqMultiset extends MultisetFactory[SeqMultiset] {
  implicit def canBuildFrom[A]: GenericCanBuildFrom[A] = ReusableCBF.asInstanceOf[GenericCanBuildFrom[A]]

  def newBuilder[A] = new SeqMultisetBuilder[A]
}

/**
 * A multiset backed by a sequence. Additional elements will cause the underlying sequence to grow, making this
 * implementation sensitive to issues of scale.
 *
 * Accessing the multiplicity of a member is neither fast nor cheap. Each call will incur one traversal on the backing
 * sequence.
 *
 * Iteration will match the building order of the underlying sequence.
 *
 * @param elems A sequence of members
 * @tparam A The type of each member of the set
 */

class SeqMultiset[A](elems: Seq[A])
  extends Multiset[A]
  with GenericTraversableTemplate[A, SeqMultiset]
  with MultisetLike[A, SeqMultiset[A]]
{
  def count(element: A): Int = elems.count(_ == element)

  def contains(element: A): Boolean = elems.contains(element)

  def iterator: Iterator[A] = elems.iterator

  override lazy val size: Int = elems.length

  override def companion: GenericCompanion[SeqMultiset] = SeqMultiset

  override def hashCode(): Int = elems.hashCode()
}

/**
 * A builder for multisets backed by a sequence.
 *
 * @tparam A The type of the members in the multiset
 */

class SeqMultisetBuilder[A] extends mutable.Builder[A, SeqMultiset[A]] {
  private val elements = mutable.ListBuffer[A]()

  def +=(elem: A) = { elements += elem; this }

  def result() = new SeqMultiset(elements)

  def clear() = elements.clear()
}
