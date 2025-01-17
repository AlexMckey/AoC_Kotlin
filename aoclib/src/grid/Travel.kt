package grid

import pos.PosXY

abstract class Travel(open val g: CharGrid) {
    abstract val start: Walker
    abstract fun isStop(st: Walker): Boolean
    abstract fun isBlocked(st: Walker): Boolean
    abstract fun changeStateRule(st: Walker): Walker

    fun traverse(): Pair<Set<PosXY>, Boolean> {
        val seen: MutableSet<Walker> = mutableSetOf()
        var cur = start
        while (!isStop(cur) && !seen.contains(cur)) {
            seen.add(cur)
            cur = changeStateRule(cur)
        }
        return seen.map { it.p }.toSet() to g.contains(cur.p)
    }
}