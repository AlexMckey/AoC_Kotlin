package year2024.day05

import splitByBlankLines

val s = "47|53\n" +
        "97|13\n" +
        "97|61\n" +
        "97|47\n" +
        "75|29\n" +
        "61|13\n" +
        "75|53\n" +
        "29|13\n" +
        "97|29\n" +
        "53|29\n" +
        "61|53\n" +
        "97|53\n" +
        "61|29\n" +
        "47|13\n" +
        "75|47\n" +
        "97|75\n" +
        "47|61\n" +
        "75|61\n" +
        "47|29\n" +
        "75|13\n" +
        "53|13\n" +
        "\n" +
        "75,47,61,53,29\n" +
        "97,61,53,29,13\n" +
        "75,29,13\n" +
        "75,97,47,61,53\n" +
        "61,13,29\n" +
        "97,13,75,29,47"

fun <T> List<T>.middle(): T =
    this[lastIndex / 2]

val bs = s.splitByBlankLines()
val rules = bs.first().split("\n").map { it.split('|').map { it.toInt() }.let { it.first() to it.last() } }.toSet()
rules
val prints = bs.last().split("\n").map{ it.split(',').map { it.toInt() } }
prints
val p1 = prints.first()
p1
val cmp = Comparator{i1: Int, i2: Int ->
    when {
        rules.contains(i1 to i2) -> -1
        rules.contains(i2 to i1) -> 1
        else -> 0
    }
}
p1.sortedWith(cmp)
prints.filter { it == it.sortedWith(cmp) }.sumOf { it.middle() }