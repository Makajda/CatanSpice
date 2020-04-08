package com.makajda.catanspice

import com.google.gson.annotations.Expose

class Map {
    val slots = ArrayList<Slot>()

    fun copyFrom(initSlots: ArrayList<Slot>?) {
        if(initSlots!=null) {
            for (slot in initSlots) {
                val newSlot = Slot(slot.x, slot.z)
                newSlot.prod = slot.prod
                newSlot.jetton = slot.jetton
                newSlot.settlement = slot.settlement
                newSlot.isUp = slot.isUp
                slots.add(newSlot)
            }
        }
    }

    fun create() {
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
