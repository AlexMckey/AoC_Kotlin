package year2024.day14

import exts.groupCount
import exts.head
import exts.second
import pos.Dir
import pos.PosXY

val s = "p=0,4 v=3,-3\n" +
        "p=6,3 v=-1,-3\n" +
        "p=10,3 v=-1,2\n" +
        "p=2,0 v=2,-1\n" +
        "p=0,0 v=1,3\n" +
        "p=3,0 v=-2,-2\n" +
        "p=7,6 v=-1,-3\n" +
        "p=3,0 v=-1,-2\n" +
        "p=9,3 v=2,3\n" +
        "p=7,3 v=-1,2\n" +
        "p=2,4 v=2,-3\n" +
        "p=9,5 v=-3,-3"

data class Robot(val p: PosXY, val v: PosXY){
    fun after(time: Int, max: PosXY): Robot =
        copy(p = (p + v * time).wrapAround(max))
}

fun PosXY.quadrant(center: PosXY): Dir =
    (this - center).sign

fun List<Robot>.safetyFactor(center: PosXY): Long =
    map { it.p.quadrant(center) }
        .filterNot { it.x == 0 || it.y == 0 }
        .groupCount()
        .values
        .map { it.toLong() }
        .reduce(Long::times)

val nR = "(-?\\d+)".toRegex()

val area = PosXY(11, 7)
val center = area / 2
val robots = nR
    .findAll(s)
    .map { it.value }
    .map { it.toInt() }
    .windowed(2, 2) { PosXY(it.head, it.second) }
    .windowed(2, 2) { Robot(it.head, it.second) }
    .toList()
robots
val newRs = robots.map{ it.after(100, area) }
newRs.map { it.p.quadrant(center) }
    .filterNot { it.x == 0 || it.y == 0 }
    .groupCount()
    .values
    .map { it.toLong() }
    .reduce(Long::times)
newRs.safetyFactor(center)