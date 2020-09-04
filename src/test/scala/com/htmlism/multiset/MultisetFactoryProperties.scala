package com.htmlism.multiset

import scala.collection.generic.GenericTraversableTemplate

import org.scalacheck.Prop.forAll
import org.scalacheck.Prop.propBoolean
import org.scalacheck.Properties

abstract class MultisetLikeFactoryProperties[CC[X] <: Multiset[X] with GenericTraversableTemplate[X, CC]](
    factory: MultisetFactory[CC]
) extends Properties(factory.getClass + " factory") {
  type X = Char

  property("count construction") = forAll { (wrapped: Seq[(X, MemorySafeTraversableLength)]) =>
    val counts = wrapped.map(t => t._1 -> t._2.count)
    val sum    = counts.iterator.map(_._2).filter(_ > 0).sum

    val set = factory.fromCounts(counts: _*)

    ("sum" |: set.size == sum) &&
    ("contains" |: counts.forall(e => set(e._1) >= e._2))
  }
}

class MultisetFactoryProperties extends MultisetLikeFactoryProperties(Multiset)

class MapMultisetFactoryProperties extends MultisetLikeFactoryProperties(MapMultiset)

class SeqMultisetFactoryProperties extends MultisetLikeFactoryProperties(SeqMultiset)
