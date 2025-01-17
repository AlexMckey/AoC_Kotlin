package pos

import kotlin.math.absoluteValue

typealias Dir = PosXY

object Dirs {
    val N get() = PosXY(0, -1)
    val E get() = PosXY(1, 0)
    val S get() = PosXY(0, 1)
    val W get() = PosXY(-1, 0)
    val NE get() = PosXY(1, -1)
    val SE get() = PosXY(1, 1)
    val NW get() = PosXY(-1, -1)
    val SW get() = PosXY(-1, 1)
    val Nop get() = PosXY.Zero

    val Axis: List<Dir> = listOf(N, E, S, W)
    val Diag: List<Dir> = listOf(NE, SE, SW, NW)
    val All = Axis + Diag
}

private val gridDirChars = Dirs.Axis.zip(listOf('^','>','v','<')).toMap()
private val charGridDirs = gridDirChars.map { it.value to it.key }.toMap()
private val dirStrs = Dirs.All.zip(listOf("N","E","S","W","NE","SE","SW","NW")).toMap()

object GridDir {
    val L = Dirs.W
    val R = Dirs.E
    val U = Dirs.N
    val D = Dirs.S
    fun of(ch: Char): Dir {
        require(charGridDirs.containsKey(ch))
        return charGridDirs[ch]!!
    }
}

fun Dir.toGridDirChar(): Char {
    require(gridDirChars.containsKey(this))
    return gridDirChars[this]!!
}
fun Dir.toStr(): String {
    require(dirStrs.containsKey(this))
    return dirStrs[this]!!
}

val Dir.rotL45: PosXY get() {
    require(x.absoluteValue <= 1 && y.absoluteValue <= 1)
    return (this + rotL).sign
}

val Dir.rotR45: PosXY get() {
    require(x.absoluteValue <= 1 && y.absoluteValue <= 1)
    return (this + rotR).sign
}

fun Dir.simplify(): List<Dir> =
    if (Dirs.Diag.contains(this))
        listOf(this.copy(x = 0), this.copy(y = 0))
    else listOf(this)

fun PosXY.toDir(d: Dir, cnt: Int = 1): PosXY =
    generateSequence(this){ it + d }.drop(cnt).first()

fun PosXY.goToDir(d: Dir): Sequence<PosXY> =
    generateSequence(this){ it + d }