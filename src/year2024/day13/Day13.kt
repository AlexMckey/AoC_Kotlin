package year2024.day13

import SomeDay
import pos.PosXY
import year2024.DayOf2024

data class ClawMachine(val a: PosXY, val b: PosXY, val p: PosXY){
    fun calcPushButtons(d: Long = 0L): Pair<Long, Long>? {
        val (an, ad) = b.x * (p.y + d) - b.y * (p.x + d) to b.x.toLong() * a.y - b.y * a.x
        val (bn, bd) = a.x * (p.y + d) - a.y * (p.x + d) to a.x.toLong() * b.y - a.y * b.x
        return if (bd == 0L || bn % bd != 0L || ad == 0L || an % ad != 0L) null
        else an / ad to bn / bd
    }
}

val numsR = "(\\d+)".toRegex()

val Sequence<Pair<Long,Long>?>.cost: Long get() = filterNotNull().sumOf{ (a,b) -> a*3 + b }

object Day13: DayOf2024(13, "Claw Contraption") {

    private val cms = numsR
        .findAll(data)
        .map { it.value.toInt() }
        .windowed(2, 2) { (a, b) -> PosXY(a, b) }
        .windowed(3, 3).map { (p1, p2, r) -> ClawMachine(p1, p2, r) }

    override fun first(): Any? =
        cms.map { it.calcPushButtons() }.cost

    override fun second(): Any? =
        cms.map { it.calcPushButtons(10000000000000L) }.cost
}

fun main() = SomeDay.mainify(Day13)

//Year 2024, Day 13 : Claw Contraption
//  Part 1: 28138
//    Time: 13ms
//  Part 2: 108394825772874
//    Time: 2ms