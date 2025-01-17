package grid

import pos.Dir
import pos.PosXY

data class Walker(val p: PosXY, val d: Dir) {
    val step: Walker get() = copy(p = p + d)
    val prev: Walker get() = copy(p = p - d)
    val turnR: Walker get() = copy(d = d.rotR)
    val turnL: Walker get() = copy(d = d.rotL)
    fun goWhile(f: (Walker) -> Boolean): Sequence<Walker> =
        generateSequence(this){ w -> w.step }.takeWhile { f(it) }
}