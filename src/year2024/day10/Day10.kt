package year2024.day10

import SomeDay
import pos.Dirs
import pos.PosXY
import year2024.DayOf2024
import traverse.traverseWithCount


object Day10: DayOf2024(10, "Hoof It") {

    private val starts = grid.filter { it == '0' }.allPos
    private val ends = grid.filter { it == '9' }.allPos
    private val near = grid.near(Dirs.Axis){ g, p, d -> g[d]!! - g[p]!! == 1}
    private val trailheads: List<Map<PosXY, Int>> by lazy {
        starts
            .map { traverseWithCount(it, near)
                .filter { ends.contains(it.key) }
                .toMap()
            }
    }

    override fun first(): Any? =
        trailheads.sumOf { it.keys.size }

    override fun second(): Any? =
        trailheads.sumOf { it.values.sum() }
}

fun main() = SomeDay.mainify(Day10)

//Year 2024, Day 10 : Hoof It
//  Part 1: 674
//    Time: 20ms
//  Part 2: 1372
//    Time: 1ms