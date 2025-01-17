package year2024.day13

import match.int
import pos.PosXY

val s = "Button A: X+94, Y+34\n" +
        "Button B: X+22, Y+67\n" +
        "Prize: X=8400, Y=5400\n" +
        "\n" +
        "Button A: X+26, Y+66\n" +
        "Button B: X+67, Y+21\n" +
        "Prize: X=12748, Y=12176\n" +
        "\n" +
        "Button A: X+17, Y+86\n" +
        "Button B: X+84, Y+37\n" +
        "Prize: X=7870, Y=6450\n" +
        "\n" +
        "Button A: X+69, Y+23\n" +
        "Button B: X+27, Y+71\n" +
        "Prize: X=18641, Y=10279"

data class ClawMachine(val a: PosXY, val b: PosXY, val p: PosXY){
    fun calcPushButtons(d: Long = 0L): Pair<Long, Long>? {
        val (an, ad) = b.x * (p.y + d) - b.y * (p.x + d) to b.x.toLong() * a.y - b.y * a.x
        val (bn, bd) = a.x * (p.y + d) - a.y * (p.x + d) to a.x.toLong() * b.y - a.y * b.x
        return if (bd == 0L || bn % bd != 0L || ad == 0L || an % ad != 0L) null
        else an / ad to bn / bd
    }
}

val posR = "X.(?<X>\\d+).+Y.(?<Y>\\d+)".toRegex()
val numsR = "(\\d+)".toRegex()

val List<Pair<Long,Long>>.cost: Long get() = sumOf{ (a,b) -> a*3 + b }

val ps = posR.findAll(s).map { PosXY(it.int("X"), it.int("Y")) }.toList()
ps
val ns = numsR.findAll(s).map { it.value.toInt() }
val pss = ns.windowed(2, 2) { (a, b) -> PosXY(a, b) }
val cms = pss.windowed(3, 3).map { (p1, p2, r) -> ClawMachine(p1, p2, r) }
cms.toList()
cms.map { it.calcPushButtons() }.filterNotNull().toList().cost

