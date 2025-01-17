@file:Suppress("unused")

package grid

import pos.Box
import pos.Neighbors
import pos.PosXY
import pos.boundingBox

typealias CharGrid = Grid<Char>

open class Grid<A>(val mg: Map<PosXY, A>) {

    constructor(sl: List<String>, f: (Char) -> A) : this(
        sl.flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                PosXY(x, y) to f(char)}}
            .associate { it })

    fun mkString(firstRow: String = "\n",
                 rowSep: String = "\n",
                 lastRow: String = "",
                 firstPos: String = "",
                 posSep: String = "",
                 lastPos: String = "",
                 defChar: String = " ",
                 mapf: (A) -> String = {it.toString()}): String
    {
        val b = gridBox
        return (b.first.y..b.second.y)
            .joinToString(rowSep, firstRow, lastRow) { y ->
            (b.first.x..b.second.x)
                .joinToString(posSep, firstPos, lastPos) { x ->
                    get(PosXY(x, y))?.let { mapf(it) } ?: defChar
                }
        }
    }

    override fun toString(): String = mkString()

    operator fun get(p: PosXY): A? = mg[p]

    operator fun set(p: PosXY, a: A): Grid<A> =
        Grid(mg + (p to a))

    val gridBox: Box get() = mg.keys.toList().boundingBox()

    operator fun contains(p: PosXY): Boolean =
        mg.containsKey(p)

    fun transpose(): Grid<A> =
        Grid(mg.mapKeys { it.key.swap })

    fun sequence(): Sequence<Pair<PosXY, A>> =
        mg.map { it.toPair() }.asSequence()

    fun filter(f: (A) -> Boolean): Grid<A> =
        Grid(mg.filterValues(f))

    fun filterWithPos(pf: (PosXY, A) -> Boolean): Grid<A> =
        Grid(mg.filter { pf(it.key, it.value) })

    val allPos: List<PosXY> get() = mg.keys.toList()

    fun posOf(a: A): PosXY? =
        mg.firstNotNullOfOrNull { if (it.value == a) it.key else null }

    fun count(f: (A) -> Boolean): Int =
        mg.values.count { f(it) }

    fun <B> mapWithPos(f: (PosXY, A) -> B): Grid<B> =
        Grid(mg.mapValues { f(it.key, it.value) })

    fun <B> map(f: (A) -> B): Grid<B> =
        Grid(mg.mapValues { f(it.value) })

    fun isEq(p: PosXY, a: A): Boolean =
        mg[p] == a

    fun getOrElse(p: PosXY, def: A): A =
        mg.getOrDefault(p, def)

    fun near(dirs: List<PosXY>, f: (Grid<A>, PosXY, PosXY) -> Boolean): Neighbors<PosXY> =
        {p -> dirs.map{ it + p }.filter{ contains(it) }.filter{ f(this, p, it) }}

    companion object {
        fun of(l: List<String>): Grid<Char> = Grid(l){it}
    }
}

fun <T> PosXY.inBounds(grid: Grid<T>) = grid.contains(this)
