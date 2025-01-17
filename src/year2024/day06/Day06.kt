package year2024.day06

import SomeDay
import grid.CharGrid
import grid.Travel
import grid.Walker
import pos.Dirs
import year2024.DayOf2024

data class GuardTravel(override val g: CharGrid): Travel(g) {
    override val start: Walker
        get() = Walker(g.posOf('^')!!, Dirs.N)

    override fun isStop(st: Walker): Boolean = g.get(st.p) == null

    override fun isBlocked(st: Walker): Boolean = g.isEq(st.p,'#')

    override fun changeStateRule(st: Walker): Walker {
        val fwd = st.step
        return if (isBlocked(fwd))
            st.turnR
        else fwd
    }
}

object Day06: DayOf2024(6, "Guard Gallivant") {

    override fun first(): Any? =
        GuardTravel(grid).traverse().first.size

    override fun second(): Any? {
        val gt = GuardTravel(grid)
        val path = gt.traverse().first - gt.start.p
        return path
            .toList().map { p ->
                val newGT = grid.set(p, '#')
                val st = GuardTravel(newGT).traverse().second
                p to st
            }.count { it.second }
    }
}

fun main() = SomeDay.mainify(Day06)

//Year 2024, Day 6 : Guard Gallivant
//  Part 1: 4580
//    Time: 114ms
//  Part 2: 1480
//    Time: 3.852s