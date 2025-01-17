package year2024.day11

import SomeDay
import year2024.DayOf2024
import memo.memoize

val Long.hasEvenDigits: Boolean get() = toString().length % 2 == 0

val Long.split: List<Long> get() {
    val s = toString()
    return s.chunked(s.length / 2).map { it.toLong() }
}

val blink = memoize<Pair<Long,Int>, Long> { f ->
    { p ->
        val (stone, step) = p
        when {
            step == 0 -> 1L
            stone == 0L -> f(1L to step-1)
            stone.hasEvenDigits -> stone.split.sumOf { f(it to step-1) }
            else -> f(stone * 2024 to step-1)
        }
    }
}

object Day11: DayOf2024(11, "Plutonian Pebbles") {

    private val stones = data.split(' ').map { it.toLong() }

    override fun first(): Any? =
        stones.sumOf { blink(it to 25) }

    override fun second(): Any? =
        stones.sumOf { blink(it to 75) }
}

fun main() = SomeDay.mainify(Day11)

//Year 2024, Day 11 : Plutonian Pebbles
//  Part 1: 198075
//    Time: 8ms
//  Part 2: 235571309320764
//    Time: 88ms