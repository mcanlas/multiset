multiset
========

A **multiset** is a set with duplicate members.

As of version 2.11, Scala's collections framework does not include multisets. This implementation seeks to fill that void.

Usage
-----

```
import com.htmlism.Multiset

val fruits = Multiset('apple -> 2, 'orange -> 3)
fruits.size == 5

val moreFruits = fruits + 'strawberry
moreFruits('strawberry) == 1
moreFruits.elements == Set('strawberry, 'orange, 'apple)

val rosales = moreFruits without 'orange
rosales == Multiset('strawberry -> 1, 'apple -> 2)
```

Combinatorics
-------------

Included with the collection class is an iterator for submultisets of a given multiset and length.

Colophon
--------

I was motivated to create this framework to more easily generate and analyze combinations of cards in collectable card games like Magic: The Gathering and Hearthstone.
