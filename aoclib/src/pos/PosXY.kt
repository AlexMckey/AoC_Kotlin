package pos

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sign
import kotlin.math.sqrt

import exts.headAndTail
import math.wrapAround

data class PosXY(var x: Int = 0, var y: Int = 0) {
    override fun toString(): String = "[$x,$y]"
    operator fun plus(p: PosXY) = PosXY(p.x + x, p.y + y)
    operator fun minus(p: PosXY) = PosXY(x - p.x, y - p.y)
    operator fun plusAssign(p: PosXY) = run { x += p.x; y += p.y }
    operator fun minusAssign(p: PosXY) = run { x -= p.x; y -= p.y }
    operator fun unaryMinus() = PosXY(-x, -y)
    operator fun times(k: Int) = PosXY(x * k, y * k)
    operator fun times(p: PosXY) = PosXY(x * p.x, y * p.y)
    operator fun timesAssign(k: Int) = run { x *= k; y *= k }
    fun cross(p: PosXY): Long = x.toLong() * p.y - p.x * y
    operator fun div(k: Int) = PosXY(x / k, y / k)
    infix fun max(p: PosXY): PosXY = PosXY(x.coerceAtLeast(p.x), y.coerceAtLeast(p.y))
    infix fun min(p: PosXY): PosXY = PosXY(x.coerceAtMost(p.x), y.coerceAtMost(p.y))
    operator fun rem(p: Int): PosXY = PosXY(x % p, y % p)
    operator fun rem(p: PosXY): PosXY = PosXY(x % p.x, y % p.y)

    fun wrapAround(max: PosXY): PosXY = PosXY(x.wrapAround(max.x), y.wrapAround(max.y))
    fun angle(p: PosXY): Double {
        val (x,y) = (p - this).pair
        return Math.toDegrees(atan2(y.toDouble(), x.toDouble()))
    }
    fun distance(p: PosXY): Double {
        val (x,y) = (p - this).pair
        return sqrt(1.0 * x * x + y * y)
    }
    fun manhattanDistance(p: PosXY = Zero): Int {
        return abs(this.x - p.x) + abs(this.y - p.y)
    }

    val sign: PosXY get() = PosXY(x.sign, y.sign)
    val swap: PosXY get() = PosXY(y, x)
    val rotL: PosXY get() = PosXY(y, -x)
    fun rotL(cnt: Int): PosXY = generateSequence(this){ it.rotL }.drop(cnt).first()
    val rotR: PosXY get() = PosXY(-y, x)
    fun rotR(cnt: Int): PosXY = generateSequence(this){ it.rotR }.drop(cnt).first()
    val flip: PosXY get() = PosXY(-x, -y)

    fun manhattanMovesTo(p: PosXY): List<Dir> =
        (p - this).sign.simplify()

    companion object {
        fun of(s: String): PosXY = s
            .split(Regex("[,;]"))
            .map(String::toInt)
            .let { PosXY(it.first(), it.last()) }
        val Zero get() = PosXY(0, 0)
        val Start = Zero
    }

    fun near(dirs: List<Dir>, f: (PosXY) -> Boolean): List<PosXY> = dirs.map { this + it }.filter(f)
    val allNears: List<PosXY> get() = near(Dirs.All){true}

    operator fun compareTo(other: PosXY): Int {
        val d = this - other
        return if (d.x == 0) d.y else d.x
    }
}

val Pair<Int,Int>.of: PosXY get() = PosXY(first, second)

val PosXY.pair: Pair<Int,Int> get() = this.x to this.y
fun PosXY.inBounds(xRange: IntRange, yRange: IntRange): Boolean = this.x in xRange && this.y in yRange
fun PosXY.inArea(p1: PosXY, p2: PosXY): Boolean {
    require(p1 <= p2)
    //return x >= p1.x && x <= p2.x && y >= p1.y && y <= p2.y
    return this.inBounds(p1.x..p2.x, p1.y..p2.y)
}
fun PosXY.inArea(pr: Box): Boolean = this.inArea(pr.first, pr.second)
fun PosXY.inArea(p: PosXY): Boolean = this.inArea(PosXY.Zero, p)

fun Iterable<PosXY>.boundingBox(): Box {
    val (init, tail) = this.headAndTail()
    return tail.fold(init to init){ acc, p ->
        (acc.first min p) to (acc.second max p)
    }
}