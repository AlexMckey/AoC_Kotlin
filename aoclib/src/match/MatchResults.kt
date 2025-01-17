package match

fun MatchResult.int(name: String) = groups[name]!!.value.toInt()
fun MatchResult.long(name: String) = groups[name]!!.value.toLong()
fun MatchResult.string(name: String) = groups[name]!!.value