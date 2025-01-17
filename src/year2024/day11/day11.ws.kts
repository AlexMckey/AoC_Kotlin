package year2024.day11

import memo.*

val stones = "125 17".split(' ').map { it.toLong() }
stones

val Long.hasEvenDigits: Boolean get() = toString().length % 2 == 0
val Long.split: List<Long> get() {
    val s = toString()
    return s.chunked(s.length / 2).map { it.toLong() }
}

125L.hasEvenDigits
17L.hasEvenDigits
17L.split

val blink = memoize<Pair<Long,Int>, Long> { f ->
    { p: Pair<Long,Int> ->
        val (stone, step) = p
        when {
            step == 0 -> 1L
            stone == 0L -> f(1L to step-1)
            stone.hasEvenDigits -> stone.split.sumOf { f(it to step-1) }
            else -> f(stone * 2024 to step-1)
        }
    }
}
blink(175L to 25)
stones.sumOf { blink(it to 25) }
stones.sumOf { blink(it to 75) }