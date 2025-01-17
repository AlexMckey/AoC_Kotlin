package year2024.day10

import grid.Grid
import pos.Dirs
import pos.Neighbors

val s = "89010123\n" +
        "78121874\n" +
        "87430965\n" +
        "96549874\n" +
        "45678903\n" +
        "32019012\n" +
        "01329801\n" +
        "10456732"

val g = Grid(s.split("\n")){it}//{it.digitToInt()}

val starts = g.filter { it == '0' }.allPos
starts
val ends = g.filter { it == '9' }.allPos
val near = g.near(Dirs.Axis){ g, p, d -> g[d]!! - g[p]!! == 1}

fun <A> traverse(start: A, neighbors: Neighbors<A>): Map<A, Int> {
    val visitedCnt: MutableMap<A, Int> = mutableMapOf()//.withDefault { 0 }
    val toVisit: ArrayDeque<A> = ArrayDeque()
    toVisit.addFirst(start)
    while (toVisit.isNotEmpty()) {
        val node = toVisit.removeFirst()
        visitedCnt[node] = visitedCnt.getOrDefault(node,0) + 1
        toVisit.addAll(neighbors(node))
    }
    return visitedCnt.toMap()
}
near(starts.first())

val trailheads = starts
    .map { traverse(it, near)
        .filter { ends.contains(it.key) }
        .toMap()
    }

trailheads.sumOf { it.keys.size }

trailheads.sumOf { it.values.sum() }