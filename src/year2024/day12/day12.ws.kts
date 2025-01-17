package year2024.day12

import grid.Grid
import pos.*
import traverse.components
import kotlin.math.absoluteValue

//val s = "AAAA\n" +
//        "BBCD\n" +
//        "BBCC\n" +
//        "EEEC"
val s = "RRRRIICCFF\n" +
        "RRRRIICCCF\n" +
        "VVRRRCCFFF\n" +
        "VVRCCCJFFF\n" +
        "VVVVCJJCFE\n" +
        "VVIVCCJJEE\n" +
        "VVIIICJJEE\n" +
        "MIIIIIJJEE\n" +
        "MIIISIJEEE\n" +
        "MMMISSJEEE"

val g = Grid(s.split('\n')){it}
val ns = g.near(Dirs.Axis){g,p,d -> g[p]!! == g[d]!!}
val cs = components(g.allPos.toSet(), ns)
cs.size

val Set<PosXY>.area get() = size
val Set<PosXY>.perimeter get() =
    sumOf { p ->  Dirs.Axis.count { d -> !this.contains(p+d) } }

val r1 = cs.first()
g[r1.first()]
r1.area
r1.perimeter

cs.map { it.area * it.perimeter }
cs.sumOf { it.area * it.perimeter }


val (p1,p2) = r1.boundingBox()
val b = Box(p1.toDir(Dirs.NW), p2)
b
val ps = b.sequence().map { listOf(it, it.toDir(Dirs.S), it.toDir(Dirs.E), it.toDir(Dirs.SE)) }
ps.toList()
ps.sumOf{ it.map{ if (r1.contains(it)) 1 else 0 }
    .zip(listOf(1,-1,-1,1), Int::times).sum().absoluteValue}

val p3 = r1.first()
p3
Dirs.Axis.map { dir ->
    listOf(p3 + dir, p3 + dir.rotL, p3 + dir.flip)
}

val Set<PosXY>.sides: Int get() =
    sumOf { loc ->
        Dirs.Axis.count { dir ->
            !contains(loc + dir) && (!contains(loc + dir.rotR) || contains(loc + dir.rotR45))
        }
    }
val Set<PosXY>.sidesAlt: Int get() {
    val (p1,p2) = this.boundingBox()
    val b = Box(p1.toDir(Dirs.NW), p2)
    val ps = b.sequence().map { listOf(it, it.toDir(Dirs.S), it.toDir(Dirs.E), it.toDir(Dirs.SE)) }
    return ps.sumOf{ it.map{ if (contains(it)) 1 else 0 }
        .zip(listOf(1,-1,-1,1), Int::times).sum().absoluteValue}
}
cs.first().sides
cs.first().sidesAlt