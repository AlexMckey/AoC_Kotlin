package traverse

import pos.Neighbors

fun <A> floodfill(start: A, n: Neighbors<A>): Set<A> {
    tailrec fun rec(visited: Set<A>, toVisit: Set<A>): Set<A> =
        if (toVisit.isEmpty()) visited
        else {
            val cur = toVisit.first()
            val ns = n(cur) - visited
            val newVisited = visited + ns
            val newOpen = toVisit + ns - cur
            rec(newVisited, newOpen)
        }
    return rec(setOf(start), setOf(start))
}