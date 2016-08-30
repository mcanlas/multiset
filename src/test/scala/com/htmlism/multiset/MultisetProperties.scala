package com.htmlism.multiset

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable
import scala.util._

import org.scalacheck.Prop.{ BooleanOperators, forAll }
import org.scalacheck.Properties

abstract class MultisetLikeProperties(name: String) extends Properties(name) {
  implicit def cbf: CanBuildFrom[Nothing, A, Multiset[A]]

  type A = Char

  def fromElements(elements: Seq[A]): Multiset[A]

  def builder: mutable.Builder[A, Multiset[A]]

  property("building implicitly") = forAll { (set: Multiset[A]) =>
    (set ++ set).size == set.size * 2
  }

  property("building and clearing") = forAll { (elements: Seq[A]) =>
    val b = builder

    elements.foreach { b += _ }
    b.clear()

    b.result().isEmpty
  }

  property("multiplicity") = forAll { (element: A, lowCount: MemorySafeTraversableLength) =>
    val count = lowCount.count

    val b = builder

    for (_ <- 1 to count)
      b += element

    b.result()(element) == (if (count > 0) count else 0)
  }

  property("containment") = forAll { (elements: Seq[A]) =>
    val set = fromElements(elements)

    elements.toSet.forall(set.contains)
  }

  property("uniqueness") = forAll { (elements: Seq[A]) =>
    fromElements(elements).elements == elements.toSet
  }

  property("addition") = forAll { (set: Multiset[A], element: A, lowCount: MemorySafeTraversableLength) =>
    val count = lowCount.count

    (set + (element, count))(element) == set(element) + Math.max(count, 0)
  }

  property("subtraction") = forAll { (set: Multiset[A], element: A, subtractionCount: Int) =>
    (set - (element, subtractionCount))(element) == Math.max(set(element) - Math.max(subtractionCount, 0), 0)
  }

  property("subtraction (contains guaranteed)") = forAll { (maybeSet: Multiset[A], element: A, include: PositiveMemorySafeTraversableLength, subtractionCount: Int) =>
    // the current generator for sets will be at most 1000 of mostly different elements
    // making the worst case scenario 1000 + 10_000, well under int max and still performant
    val withSet = maybeSet + (element, include.count)

    (withSet - (element, subtractionCount))(element) == Math.max(withSet(element) - Math.max(subtractionCount, 0), 0)
  }

  property("without") = forAll { (someSet: Multiset[A], element: A) =>
    val withSet = someSet + element

    someSet.without(element) == withSet.without(element)
  }

  property("maximum") = forAll { (set: Multiset[A], maximum: Int) =>
    val setWithMaximum = set.withMaximum(maximum)
    val ceiling = Math.max(maximum, 0)

    set.elements.forall(e => setWithMaximum(e) <= ceiling)
  }

  property("combinations") = forAll { (set: Multiset[A], n: Int) =>
    val combinations = set.combinations(n)

    if (n < 0)
      combinations.isEmpty
    else if (n == 0)
      combinations.toSeq == Seq(Multiset.empty)
    else if (n > set.size)
      combinations.isEmpty
    else if (n == set.size)
      combinations.toSeq == Seq(set)
    else
      combinations.nonEmpty
  }

  property("value equality") = forAll { (elements: Seq[A]) =>
    val a, b = fromElements(elements)

    ("equals" |: a == b) &&
      ("hashcode" |: a.hashCode == b.hashCode)
  }

  property("value semantics across concrete types") = forAll { (elements: Seq[A]) =>
    val m = MapMultiset(elements: _*)
    val s = SeqMultiset(elements: _*)

    ("equals" |: m == s) &&
      ("hashcode" |: m.hashCode == s.hashCode)
  }
}

class MultisetProperties extends MultisetLikeProperties("Multiset") {
  def cbf: CanBuildFrom[Nothing, A, Multiset[A]] = Multiset.canBuildFrom[A]

  def fromElements(elements: Seq[A]) = Multiset(elements: _*)

  def builder = Multiset.newBuilder

  import org.scalacheck.Prop.forAll

  property("concrete value equality") = forAll { (elements: Seq[A]) =>
    MapMultiset(elements: _*) == new SeqMultiset(elements)
  }
}

class MapMultisetProperties extends MultisetLikeProperties("MapMultiset") {
  def cbf: CanBuildFrom[Nothing, A, Multiset[A]] = MapMultiset.canBuildFrom[A]

  def fromElements(elements: Seq[A]) = MapMultiset(elements: _*)

  def builder = MapMultiset.newBuilder

  property("overflow detection") = forAll { (firstCount: Int, secondCount: Int) =>
    val attempt = Try {
      val baseMap = MapMultiset.fromCounts(Map('apple -> firstCount))

      baseMap + ('apple, secondCount)
    }

    attempt match {
      case Success(_) => true
      case Failure(e) =>
        e match {
          case _: ArithmeticException => true
        }
    }
  }
}

class SeqMultisetProperties extends MultisetLikeProperties("SeqMultiset") {
  def cbf: CanBuildFrom[Nothing, A, Multiset[A]] = SeqMultiset.canBuildFrom[A]

  def fromElements(elements: Seq[A]) = new SeqMultiset(elements)

  def builder = SeqMultiset.newBuilder
}
