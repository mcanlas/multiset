package com.htmlism

import org.specs2.mutable.Specification

object MultisetCombinationIteratorSpec extends Specification {
  def multi(elements: Int*) = {
    var set = Multiset(Map.empty[Int, Int])

    for (e <- elements) set = set + e

    set
  }
}
