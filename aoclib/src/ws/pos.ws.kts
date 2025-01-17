package ws

import pos.*

val p1 = PosXY(-1, 2)
p1
p1.rotR
p1.rotL
val p2 = p1.rotR(2)
p2
p2.rotL(2)
p2.flip

p1.wrapAround(p1)
p2.wrapAround(p1)

p1.manhattanMovesTo(p2).map{ it.toStr() }

val p3 = PosXY(5, -2)
val p4 = PosXY(-4, 1)

val b1 = listOf(PosXY.Zero,p1,p2,p3,p4).boundingBox()
b1

p4.allNears

b1.sequence().toList()

val p01 = PosXY(2,0)
val p02 = PosXY(3,-1)
val p03 = PosXY(0,-1)
val p04 = PosXY(-4,-1)
val p05 = PosXY(-5,0)
val p06 = PosXY(-2,2)
val p07 = PosXY(0,3)
val p08 = PosXY(3,4)
p01.rotL
p01.rotR
p01.flip

p02.rotL
p02.rotR
p02.flip

p03.rotL
p03.rotR
p03.flip

p04.rotL
p04.rotR
p04.flip

p05.rotL
p05.rotR
p05.flip

p06.rotL
p06.rotR
p06.flip

p07.rotL
p07.rotR
p07.flip

p08.rotL
p08.rotR
p08.flip

Dirs.Axis.map { it.toStr() }
Dirs.Axis.map { it.rotL }.map { it.toStr() }
Dirs.Axis.map { it.rotR }.map { it.toStr() }
Dirs.Axis.map { it.flip }.map { it.toStr() }
Dirs.Axis.map { it.rotL(2) }.map { it.toStr() }
Dirs.Axis.map { it.rotR(2) }.map { it.toStr() }

Dirs.Diag.map { it.toStr() }
Dirs.Diag.map { it.rotL }.map { it.toStr() }
Dirs.Diag.map { it.rotR }.map { it.toStr() }
Dirs.Diag.map { it.flip }.map { it.toStr() }
Dirs.Diag.map { it.rotL(2) }.map { it.toStr() }
Dirs.Diag.map { it.rotR(2) }.map { it.toStr() }

Dirs.NE
Dirs.NE.flip

Dirs.Axis.map { it.toStr() }
Dirs.Axis.map{it + it.rotR}.map { it.toStr() }
Dirs.Axis.map{it + it.rotL}.map { it.toStr() }

Dirs.Axis.map { it.toStr() }
Dirs.Axis.map{it.rotR45}.map { it.toStr() }
Dirs.Axis.map{it.rotL45}.map { it.toStr() }

Dirs.Diag.map { it.toStr() }
Dirs.Diag.map{it.rotR45}.map { it.toStr() }
Dirs.Diag.map{it.rotL45}.map { it.toStr() }
