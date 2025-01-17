package ws

import exts.groupCount

val ls = listOf(1, 2, 3, 4, 2, 3, 4, 2, 2)
ls.groupBy { it }.map { it.key to it.value.size }.toMap()

ls.groupCount()