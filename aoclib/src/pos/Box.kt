package pos

typealias Box = Pair<PosXY,PosXY>

fun Box.sequence(): Sequence<PosXY> =
    generateSequence(this.first) { p ->
        if (p.x == this.second.x) PosXY(this.first.x, p.y + 1)
        else p + GridDir.R
    }.takeWhile { it.y <= this.second.y }