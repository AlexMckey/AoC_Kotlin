package year2024.day12

import SomeDay
import pos.Dirs
import pos.PosXY
import pos.rotR45
import traverse.components
import year2024.DayOf2024

val Set<PosXY>.area get() = size
val Set<PosXY>.perimeter get() =
    sumOf { p -> Dirs.Axis.count { d -> (p+d) !in this } }
val Set<PosXY>.sides: Int get() =
    sumOf { p -> Dirs.Axis.count { d ->
        (p+d) !in this && ((p+d.rotR) !in this || (p+d.rotR45) in this) } }

object Day12: DayOf2024(12, "Garden Groups") {

    private val ns = grid.near(Dirs.Axis){ g, p, d -> g[p]!! == g[d]!!}
    private val cs = components(grid.allPos.toSet(), ns)

    override fun first(): Any? =
        cs.sumOf { it.area * it.perimeter }

    override fun second(): Any? =
        cs.sumOf { it.area * it.sides }
}

fun main() = SomeDay.mainify(Day12)

//Year 2024, Day 12 : Garden Groups
//  Part 1: 1377008
//    Time: 7ms
//  Part 2: 815788
//    Time: 7ms