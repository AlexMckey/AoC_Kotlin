package year2024.day07

import SomeDay
import year2024.DayOf2024
import exts.head
import exts.tail

class Equation(pars: List<Long>) {
    val res = pars.head
    private val args = pars.tail
    fun check(ops: List<(Long, Long) -> Long>): Boolean {
        fun rec(acc: Long, args: List<Long>): Boolean =
            if (args.isEmpty()) acc == res
            else ops.any{op -> rec(op(acc,args.head), args.tail)}
        return rec(args.head, args.tail)
    }
}

val simpleOps: List<(Long,Long) -> Long> = listOf(Long::plus, Long::times)
fun concat(a: Long, b: Long): Long = "$a$b".toLong()

object Day07: DayOf2024(7, "Bridge Repair") {

    private val eqs: List<Equation> =
        lines.map { Equation(it.split("\\D+".toRegex()).map { it.toLong() }) }

    override fun first(): Any? =
        eqs
            .filter { it.check(simpleOps) }
            .sumOf { it.res }

    override fun second(): Any? =
        eqs
            .filter { it.check(simpleOps + ::concat) }
            .sumOf { it.res }
}

fun main() = SomeDay.mainify(Day07)

//Year 2024, Day 7 : Bridge Repair
//  Part 1: 1298103531759
//    Time: 113ms
//  Part 2: 140575048428831
//    Time: 449ms