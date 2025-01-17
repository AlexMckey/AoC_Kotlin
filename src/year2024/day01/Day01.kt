package year2024.day01

import SomeDay
import year2024.DayOf2024
import exts.groupCount

import kotlin.math.absoluteValue

object Day01 : DayOf2024(1, "Historian Hysteria") {
    private fun readInput(): Pair<List<Int>,List<Int>> =
        lines
            .map { it.split("""\s+""".toRegex())
                .map{ it.toInt() }
                .let { it[0] to it[1] } }
            .unzip()


    override fun first(): Any? {
        val (ids1, ids2) = readInput()
        return ids1.sorted().zip(ids2.sorted())
            .sumOf { (a, b) -> (a - b).absoluteValue }
    }

    override fun second(): Any? {
        val (ids1, ids2) = readInput()
        //val ids2Cnt = ids2.groupingBy { it }.eachCount()
        val ids2Cnt = ids2.groupCount()
        return ids1.sumOf { it * ids2Cnt.getOrDefault(it, 0) }
    }
}

fun main() = SomeDay.mainify(Day01)

//Year 2024, Day 1 : Historian Hysteria
//  Part 1: 1151792
//    Time: 87ms
//  Part 2: 21790168
//    Time: 8ms