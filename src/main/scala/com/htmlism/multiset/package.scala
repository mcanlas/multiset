package com.htmlism

/**
 * The `Multiset` trait extends Scala's collections library with an unordered collection that supports duplicate
 * elements. Contrast with `Set`, which only supports unique elements.
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
 */

package object multiset
