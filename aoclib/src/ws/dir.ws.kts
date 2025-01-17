package ws

import pos.Dirs
import pos.GridDir
import pos.PosXY
import pos.goToDir
import pos.simplify
import pos.toDir
import pos.toGridDirChar
import pos.toStr

val d1 = Dirs.E
val d2 = GridDir.U
val d3 = Dirs.SE
val p0 = PosXY.Zero
val p1 = PosXY(-1, 3)

Dirs.S
Dirs.SE

d1.toStr()
d3.toStr()
p0.toStr()
p1.toStr()

GridDir.of('^')
d1.toGridDirChar()
d2.toGridDirChar()
d3.toGridDirChar()

p0.toDir(d1)
p0.toDir(d1,2)
p0.toDir(d2)
p0.toDir(d2,3)
p0.toDir(d3)
p0.toDir(d3,4)

p1.goToDir(d3).take(10).toList()
d3.simplify()
d1.simplify()
