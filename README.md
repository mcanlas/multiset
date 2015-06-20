[![Build Status](https://travis-ci.org/mcanlas/multiset.svg?branch=master)](https://travis-ci.org/mcanlas/multiset)

multiset
========

A **multiset** is an unordered collection that supports duplicate members.

As of version 2.11, Scala's collections framework does not include multisets. This library seeks to fill that void.

Features
--------

This library is fully pimped out with Scaladoc [1], so please enjoy the meaningful output of `sbt doc`!

Because `Multiset` extends `Iterable`, it shares a wide array of functionality with Scala's collections framework.

[1] The only methods that aren't documented are those inherited from `Iterable` and `Iterator`.

Usage
-----

```scala
import com.htmlism.multiset._

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
import com.htmlism.multiset._

val fruits = Multiset.withCounts('apple -> 3, 'orange -> 7, 'strawberry -> 4)
val combinations = new MultisetCombinationIterator(fruits, 2)
val sameCombinations = fruits.combinations(2)
```

Colophon
--------

I was motivated to create this framework to more easily generate and analyze combinations of cards in collectible card games like *Magic: The Gathering* and *Hearthstone*.
