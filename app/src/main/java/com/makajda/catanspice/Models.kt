package com.makajda.catanspice

class Slot(val x: Int, val z: Int) {
    var prod = 0
    var jetton = 0

    val y: Int
        get() = -x - z

}

class Settlement(val id: Int) {
    var slot: Slot? = null
    var isUp = false
}

class Map {
    val slots = ArrayList<Slot>()
    val settlements = Array<Settlement>(Given.settlements.size * 2) {
            i -> Settlement(i / 2)
    }

    init {
        for (q in 0..Given.edge) {
            for (r in 0..Given.edge) {
                slots.add(Slot(q, -r))
                if (q != 0 || r != 0) {
                    slots.add(Slot(-q, r))
                }
            }
        }
        for (q in 1 until Given.edge) {
            for (r in 1..Given.edge - q) {
                slots.add(Slot(q, r))
                slots.add(Slot(-q, -r))
            }
        }
    }
}
