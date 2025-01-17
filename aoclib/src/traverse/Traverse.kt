package traverse

import pos.Neighbors

fun <A> traverseWithCount(start: A, neighbors: Neighbors<A>): Map<A, Int> {
    val visitedCnt: MutableMap<A, Int> = mutableMapOf()
    val toVisit: ArrayDeque<A> = ArrayDeque()
    toVisit.addFirst(start)
    while (toVisit.isNotEmpty()) {
        val node = toVisit.removeFirst()
        visitedCnt[node] = visitedCnt.getOrDefault(node,0) + 1
        toVisit.addAll(neighbors(node))
    }
    return visitedCnt.toMap()
}