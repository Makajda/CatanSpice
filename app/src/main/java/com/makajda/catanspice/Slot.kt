package com.makajda.catanspice

class Slot(val x: Int, val z: Int) {
    var prod = 0
    var jetton = 0

    val y: Int
        get() = -x - z

}
