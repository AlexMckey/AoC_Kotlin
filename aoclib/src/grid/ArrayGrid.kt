package grid

import pos.PosXY
import pos.inArea

//class ArrayCharGrid(private val ag: Array<Array<Char>>): CharGrid {
//    override fun get(p: PosXY): A? =
//        if (contains(p)) ag[p.y][p.x]
//        else null
//
//    override val gridBox: Pair<PosXY, PosXY>
//        get() = PosXY.Zero to PosXY(ag.first().size - 1, ag.size - 1)
//
//    override fun contains(p: PosXY): Boolean =
//        p.inArea(gridBox.first, gridBox.second)
//
//    override fun iterator(): Iterator<Pair<PosXY, A>> =
//        ag.flatMapIndexed{ y, row ->
//            row.mapIndexed{ x, a -> PosXY(x, y) to a}
//        }.iterator()
//
//    override fun posOf(a: A): PosXY? =
//        ag.flatMapIndexed { y, row ->
//            row.mapIndexed { x, c ->
//                if (c == a) PosXY(x, y) else null
//            }
//        }.filterNotNull().first()
//
//    override val allPos: List<PosXY> get() =
//        ag.flatMapIndexed{ y, row ->
//            row.mapIndexed{ x, a -> PosXY(x, y)}}
//
//    override fun count(f: (A) -> Boolean): Int =
//        ag.sumOf { it.count { f(it) } }
//
//    override fun isEq(p: PosXY, a: A): Boolean =
//        if (contains(p)) ag[p.y][p.x] == a
//        else false
//
//    override fun getOrElse(p: PosXY, def: A): A =
//        if (contains(p)) ag[p.y][p.x]
//        else def
//
//    override fun set(p: PosXY, a: A): Grid<A> =
//        if (contains(p)) {
//            ag[p.y][p.x] = a
//            ArrayGrid(ag)
//        } else this
//}