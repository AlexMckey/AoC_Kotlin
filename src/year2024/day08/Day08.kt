package year2024.day08

import SomeDay
import exts.head
import exts.tail
import pos.Box
import pos.PosXY
import pos.goToDir
import pos.inArea
import year2024.DayOf2024

fun twoAntiNodes(bounds: Box, n1: PosXY, n2: PosXY): Set<PosXY> {
    val d = n2 - n1
    return listOf(n1 - d, n2 + d).filter { it.inArea(bounds) }.toSet()
}

fun manyAntiNodes(bounds: Box, n1: PosXY, n2: PosXY): Set<PosXY> {
    val d = n2 - n1
    val s1 = n1.goToDir(-d).takeWhile { it.inArea(bounds) }.toSet()
    val s2 = n2.goToDir(d).takeWhile { it.inArea(bounds) }.toSet()
    return s1 + s2
}

fun calcAntiNodes(bounds: Box, ns: List<PosXY>, f: (Box, PosXY, PosXY) -> Set<PosXY>): Set<PosXY> {
    fun rec(n: PosXY, ps: List<PosXY>, acc: Set<PosXY>): Set<PosXY> =
        if (ps.isEmpty()) acc
        else rec(ps.head, ps.tail, acc + ps.flatMap { f(bounds, n, it) })
    return rec(ns.head, ns.tail, emptySet())
}

fun calcCountAllAntiNodes(ants: Collection<List<PosXY>>, box: Box, f: (Box, PosXY, PosXY) -> Set<PosXY>): Int =
    ants.fold(emptySet<PosXY>()){acc, ns -> acc + calcAntiNodes(box, ns, f)}.size

object Day08: DayOf2024(8, "Resonant Collinearity") {
    private val box = grid.gridBox
    private val ants = grid
        .filter { it != '.' }
        .sequence()
        .groupBy({it.second},{it.first})
        .values

    override fun first(): Any? =
        calcCountAllAntiNodes(ants, box, ::twoAntiNodes)

    override fun second(): Any? =
        calcCountAllAntiNodes(ants, box, ::manyAntiNodes)
}

fun main() = SomeDay.mainify(Day08)

//Year 2024, Day 8 : Resonant Collinearity
//  Part 1: 261
//    Time: 9ms
//  Part 2: 898
//    Time: 9ms