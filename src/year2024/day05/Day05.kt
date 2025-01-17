package year2024.day05

import SomeDay
import year2024.DayOf2024

class Input(bs: List<String>) {
    private val rules = bs.first().split("\n").map { it.split('|').map { it.toInt() }.let { it.first() to it.last() } }.toSet()
    val prints = bs.last().split("\n").map{ it.split(',').map { it.toInt() } }
    val cmpr: Comparator<Int> = Comparator {i1: Int, i2: Int ->
        when {
            rules.contains(i1 to i2) -> -1
            rules.contains(i2 to i1) -> 1
            else -> 0
        }
    }
}

fun <T> List<T>.middle(): T =
    this[lastIndex / 2]

object Day05 : DayOf2024(5, "Ceres Search") {
    override fun first(): Any? {
        val i = Input(blocks)
        return i
            .prints
            .filter { it == it.sortedWith(i.cmpr) }
            .sumOf { it.middle() }
    }

    override fun second(): Any? {
        val i = Input(blocks)
        return i
            .prints
            .filter { it != it.sortedWith(i.cmpr) }
            .map { it.sortedWith(i.cmpr) }
            .sumOf { it.middle() }
    }
}

fun main() = SomeDay.mainify(Day05)

//Year 2024, Day 5 : Ceres Search
//  Part 1: 6267
//    Time: 83ms
//  Part 2: 5184
//    Time: 7ms