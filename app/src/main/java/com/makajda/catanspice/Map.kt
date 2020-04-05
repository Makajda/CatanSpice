package com.makajda.catanspice

import com.google.gson.annotations.Expose

class Map {
    val slots = ArrayList<Slot>()
    val settlements = ArrayList<Settlement>()

    fun create() {
        createSlots()
        createSettlements()
    }

    fun copyFrom(initSlots: ArrayList<Slot>?, initSettlements: ArrayList<Settlement>?) {
        if(initSlots!=null && initSettlements != null) {
            for (slot in initSlots) {
                val newSlot = Slot(slot.x, slot.z)
                newSlot.prod = slot.prod
                newSlot.jetton = slot.jetton
                slots.add(newSlot)
            }

            for(settlement in initSettlements) {
                val newSettlement = Settlement(settlement.id)
                newSettlement.cross = settlement.cross
                settlements.add(newSettlement)
            }
        }
    }

    private fun createSlots() {
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

    private fun createSettlements() {
        for(i in 0 until Given.settlements.size * 2) {
            settlements.add(Settlement(i / 2))
        }
    }
}
