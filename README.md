multiset
========

A **multiset** is a set with duplicate members.

As of version 2.11, Scala's collections framework does not include multisets. This implementation seeks to fill that void.

This implementation of a multiset does not exhibit any unique performance characteristics. It's actually just a convenience wrapper around `Map[T, Int]`.

Usage
-----

```scala
import com.htmlism.Multiset

val fruits = Multiset.withCounts('apple -> 2, 'orange -> 3)
fruits.size == 5

val moreFruits = fruits + 'strawberry
moreFruits('strawberry) == 1
moreFruits.elements == Set('strawberry, 'orange, 'apple)

val rosales = moreFruits without 'orange
rosales == Multiset.withCounts('strawberry -> 1, 'apple -> 2)

val yogurt = Multiset.withCounts('strawberry -> 4, 'banana -> 3)
val strawberryHeaven = rosales ++ yogurt
strawberryHeaven('strawberry) == 5
```

Combinatorics
-------------

Included with the collection class is an iterator for submultisets of a given multiset and length.

```scala
import com.htmlism.MultisetCombinationIterator

val fruits = Multiset.withCounts('apple -> 3, 'orange -> 7, 'strawberry -> 4)
val combinations = new MultisetCombinationIterator(fruits, 2)
```

Colophon
--------

I was motivated to create this framework to more easily generate and analyze combinations of cards in collectable card games like *Magic: The Gathering* and *Hearthstone*.
