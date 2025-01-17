package year2024.day08

import grid.Grid
import pos.PosXY
import pos.inArea
import exts.head
import exts.tail
import pos.Box
import pos.goToDir

val s = "............\n" +
        "........0...\n" +
        ".....0......\n" +
        ".......0....\n" +
        "....0.......\n" +
        "......A.....\n" +
        "............\n" +
        "............\n" +
        "........A...\n" +
        ".........A..\n" +
        "............\n" +
        "............"

val g = Grid(s.split('\n')){it}
val box = g.gridBox
box
val ants = g
    .filter { it != '.' }
    .sequence()
    .groupBy({it.second},{it.first})
    .values
ants

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

ants.fold(emptySet<PosXY>()){acc, ns -> acc + calcAntiNodes(box, ns, ::twoAntiNodes)}.size
ants.fold(emptySet<PosXY>()){acc, ns -> acc + calcAntiNodes(box, ns, ::manyAntiNodes)}.size