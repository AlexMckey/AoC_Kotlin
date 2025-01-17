val s = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
val r = "mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)".toRegex()
val ms = r.findAll(s).map { it.value }.toList()
ms
ms/*.filter { it.startsWith("mul") }*/.fold(0 to true)
{ (acc, mulOn), lst ->
    when {
        lst == "do()" -> acc to true
        lst == "don't()" -> acc to false
        lst.startsWith("mul") && mulOn ->
            acc + lst.substringAfter("(").substringBeforeLast(")").split(",").map { it.toInt() }.reduce(Int::times) to mulOn
            //(acc + r.matchEntire(lst)?.groupValues.let { (it?.get(1)?.toInt() ?: 0) * (it?.get(2)?.toInt() ?: 0) }) to mulOn
        else -> acc to mulOn
    }
}.first