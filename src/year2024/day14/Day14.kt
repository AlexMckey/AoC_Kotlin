package year2024.day14

import SomeDay
import exts.groupCount
import exts.head
import exts.second
import grid.Grid
import pos.Dir
import pos.PosXY
import year2024.DayOf2024

data class Robot(val p: PosXY, val v: PosXY){
    fun after(time: Int, max: PosXY): Robot =
        copy(p = (p + v * time).wrapAround(max))
}

fun PosXY.quadrant(center: PosXY): Dir =
    (this - center).sign

fun List<Robot>.safetyFactor(max: PosXY): Int =
    map { it.p.quadrant(max / 2) }
        .filterNot { it.x == 0 || it.y == 0 }
        .groupCount()
        .values
        .reduce(Int::times)

fun List<Robot>.findEasterEgg(max: PosXY): Pair<List<Robot>, Int> =
    generateSequence(this to 0)
    { it.first.map{ it.after(1, max) } to it.second + 1 }
        .find { it.first.groupCount{ it.p }.values.all { it <= 1 } }!!

val posR = "(-?\\d+,-?\\d+)".toRegex()

object Day14: DayOf2024(14, "Restroom Redoubt") {

    private val area = PosXY(101, 103)
    private val robots = posR
        .findAll(data)
        .map{ it.value }
        .map(PosXY::of)
        .windowed(2, 2) { Robot(it.head, it.second) }
        .toList()

    override fun first(): Any? =
        robots
            .map { it.after(100, area) }
            .safetyFactor(area)

    override fun second(): Any? {
        val (rs, res) = robots.findEasterEgg(area)
        println(Grid(rs.associate { it.p to '*' }).toString())
        return res
    }
}

fun main() = SomeDay.mainify(Day14)

//Year 2024, Day 14 : Restroom Redoubt
//  Part 1: 229421808
//    Time: 5ms
//  Part 2: 6577
//    Time: 309ms
//               *          *******************************                                  *
//                           *                             *   *
//                           *                             *
//                           *                             *
//                           *                             *                               *
//                 *         *              *              *
//                           *             ***             *
//                           *            *****            *
//                           *           *******           *                                         *
//                 *         *          *********          *
//                           *            *****            *
//                        *  *           *******           *               *
//                           *          *********          *          *
//                           *         ***********         *
//      *                    *        *************        *         *                               *
//                           *          *********          *
//                           *         ***********         *
//                *          *        *************        *
//                           *       ***************       *    *                    *       *
//                           *      *****************      *
//                           *        *************        *                                         *
//                           *       ***************       *                     *
//                      *    *      *****************      *                                   *
//    *                      *     *******************     *
//                           *    *********************    *
//        *                  *             ***             *                      *
//                           *             ***             *
//      *                    *             ***             *
//                           *                             *                         *
//   *                       *                             *             *
//  *                        *                             *          *
//         *                 *                             *
//                           *******************************                   *         *
