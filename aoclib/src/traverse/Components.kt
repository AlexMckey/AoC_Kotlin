package traverse

import pos.Neighbors

fun <A> components(start: Set<A>, n: Neighbors<A>): Set<Set<A>> {
    tailrec fun rec(nodes: Set<A>, acc: Set<Set<A>> = emptySet()): Set<Set<A>> =
        if (nodes.isEmpty()) acc
        else {
            val node = nodes.first()
            val group = floodfill(node, n)
            val rest = nodes - group
            rec(rest, acc + setOf(group))
        }
    return rec(start)
}