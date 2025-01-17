package year2024.day02

import SomeDay
import year2024.DayOf2024

import kotlin.math.absoluteValue
import kotlin.math.sign

object Day02 : DayOf2024(2, "Red-Nosed Reports") {

    private fun readInput(): List<Report> = lines.map { Report(it.split(" ").map{ it.toInt() }) }

    open class Report(private val report: List<Int>){
        private val goalSign = (report.first() - report.last()).sign
        fun isSafe(): Boolean = report.zipWithNext(Int::minus)
            .all { it.sign == goalSign && it.absoluteValue in 1..3 }
        fun isSafeWithOneBad(): Boolean = report.indices
            .any{ Report(report.slice(report.indices - it)).isSafe() }
    }

    override fun first(): Any? = readInput().count { it.isSafe() }

    override fun second(): Any?  = readInput().count { it.isSafe() || it.isSafeWithOneBad() }
}

fun main() = SomeDay.mainify(Day02)

//Year 2024, Day 2 : Red-Nosed Reports
//  Part 1: 432
//    Time: 84ms
//  Part 2: 488
//    Time: 11ms