package year2024.day02

import kotlin.math.absoluteValue
import kotlin.math.sign

val s = "7 6 4 2 1\n" +
        "1 2 7 8 9\n" +
        "9 7 6 2 1\n" +
        "1 3 2 4 5\n" +
        "8 6 4 4 1\n" +
        "1 3 6 7 9\n" +
        "16 10 13 15 17\n" +
        "13 18 15 16 17\n" +
        "10 13 15 19\n" +
        "17 18 13 15 16\n" +
        "17 10 13 15 16\n" +
        "10 13 15 18 9\n" +
        "10 13 15 18 11"

val sl = s.split("\n").map{ it.split(" ").map{ it.toInt() } }
val dl = sl.map{ it.zipWithNext(Int::minus) }
val signs = dl.map{ it.groupingBy { it.sign }.eachCount().maxBy { it.value }.key }
signs
val diffs = dl.map{ it.groupingBy { it.absoluteValue }.eachCount() }
diffs
(dl[0].all { it > 0 } || dl[0].all { it > 0 }) && dl[0].all { it.absoluteValue in 1..3 }
fun check(l: List<Int>): List<Boolean> {
    val dl = l.zipWithNext(Int::minus)
    val goalSign = dl.groupingBy { it.sign }.eachCount().maxBy { it.value }.key
    return dl.map { it.sign == goalSign && it.absoluteValue in 1..3 }
}
fun isSafe(l: List<Boolean>): Boolean = l.count { !it } == 0
fun hasOneBad(l: List<Boolean>): Boolean = l.count { !it } == 1
val checks = sl.associateWith{ check(it) }
checks.count { isSafe(it.value) }
val oneBads = checks.filter { hasOneBad(it.value) }
oneBads.size
oneBads.count {
    val idx = it.value.indexOf(false)
    listOf(it.key.slice(it.key.indices - idx),
           it.key.slice(it.key.indices - (idx + 1)))
        .any { isSafe(check(it)) }
}

