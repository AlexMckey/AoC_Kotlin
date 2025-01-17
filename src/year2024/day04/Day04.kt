package year2024.day04

import SomeDay
import pos.Dirs
import year2024.DayOf2024

object Day04 : DayOf2024(4, "Ceres Search") {
    override fun first(): Any? {
        val g = grid
        return g.filter { ch: Char -> ch == 'X' }
            .allPos
            .sumOf { p -> Dirs.All
                .count { d -> (0..<4)
                    .map { g.getOrElse(p + d * it, ' ') }
                    .joinToString("") == "XMAS" } }
    }

    override fun second(): Any? {
        val g = grid
        val pat = listOf("MSSM", "SMMS", "MMSS", "SSMM")
        return g.filter { ch: Char -> ch == 'A' }
            .allPos
                .count { p -> Dirs.Diag
                    .map { d -> g.getOrElse(p + d, ' ') }
                    .joinToString("")
                    .let { pat.contains(it) }
                }
    }
}

fun main() = SomeDay.mainify(Day04)

//Year 2024, Day 4 : Ceres Search
//  Part 1: 2549
//    Time: 117ms
//  Part 2: 2003
//    Time: 21ms