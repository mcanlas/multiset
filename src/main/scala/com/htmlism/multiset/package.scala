package com.htmlism

/**
  * A `Multiset` is an unordered collection that supports duplicate elements. Contrast with Scala's `Set`, which only
  * supports unique elements.
  *
  * The trait is backed by two concrete implementations, `MapMultiset` and `SeqMultiset`, with the former being the
  * default. Ultimately, `Multiset` is a new subtype of `Iterable`, akin to `Seq`, `Set`, and `Map`.
  *
  * All three support construction via their companion objects.
  *
  * {{{
  *   val cities     = Multiset('NewYork, 'NewYork, 'London)
  *   val characters = MapMultiset('Chrono, 'Marle, 'Lucca, 'Chrono)
  *   val elements   = SeqMultiset('Earth, 'Metal, 'Wood, 'Metal)
  * }}}
  *
  * `Multiset` extends Scala's `Iterable` and thus shares in a wealth of operations from the collections library.
  *
  * {{{
  *   cities.toList
  *   cities.filter(_ == 'London)
  *   cities.map(_ => 'Tokyo)
  * }}}
  */
package object multiset
