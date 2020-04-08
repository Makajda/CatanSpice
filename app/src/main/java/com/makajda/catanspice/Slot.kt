package com.makajda.catanspice

class Slot(val x: Int, val z: Int) {
    var prod = 0
    var jetton = 0
    var settlement = 0
    var isUp = false

    val y: Int
        get() = -x - z

}
