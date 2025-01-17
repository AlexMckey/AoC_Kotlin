@file:Suppress("KotlinConstantConditions")

package year2024.day03

import SomeDay
import year2024.DayOf2024
import match.int

object Day03 : DayOf2024(3, "Mull It Over") {

    private val r = "mul\\((?<x>\\d{1,3}),(?<y>\\d{1,3})\\)|do(?:n't)?\\(\\)".toRegex()

    private fun readInput(): Sequence<MatchResult> = r.findAll(data)

    private fun calSumMuls(ms: Sequence<MatchResult>): Int {
        return ms
            .fold(0 to true)
            { (acc, mulOn), lst ->
                when {
                    lst.value == "do()" -> acc to true
                    lst.value == "don't()" -> acc to false
                    mulOn -> acc + lst.int("x") * lst.int("y") to mulOn
                    else -> acc to mulOn
                }
            }.first
    }

    override fun first(): Any? = calSumMuls(readInput().filter { it.value.startsWith("mul") })

    override fun second(): Any?  = calSumMuls(readInput())
}

fun main() = SomeDay.mainify(Day03)

//Year 2024, Day 3 : Mull It Over
//  Part 1: 170778545
//    Time: 77ms
//  Part 2: 82868252
//    Time: 2ms