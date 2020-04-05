package com.makajda.catanspice

import com.google.gson.annotations.Expose

class Map {
    val slots = ArrayList<Slot>()
    val settlements = ArrayList<Settlement>()

    @Expose(serialize = false, deserialize = false)
    var crosses = ArrayList<Cross>()

    fun create() {
        createSlots()
        createSettlements()
        createCrosses()
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
                newSettlement.slot = settlement.slot
                newSettlement.isUp = settlement.isUp
                settlements.add(newSettlement)
            }

            createCrosses()
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

    private fun createCrosses() {
        val pairs = createPairs()
        while (pairs.size > 0) {
            val pair = pairs[0]
            pairs.remove(pair)
            addCross(pairs, pair.slot1, pair.slot2)
            addCross(pairs, pair.slot2, pair.slot1)
        }
    }

    private fun createPairs() : ArrayList<Pair> {
        val pairs = ArrayList<Pair>()
        for(slot in slots) {
            val neighbors = getNeighbors(slot)
            for (neighbor in neighbors) {
                var exist = false
                for (pair in pairs) {
                    if (pair.checkSame(slot, neighbor)) {
                        exist = true
                        break
                    }
                }
                if(!exist) {
                    pairs.add(Pair(slot, neighbor))
                }
            }
        }
        return pairs
    }

    private fun addCross(pairs: ArrayList<Pair>, slot1: Slot?, slot2: Slot?) {
        val neighbors1 = ArrayList<Pair>()
        for(pair1 in pairs) {
            if(pair1.slot1 == slot2) {
                neighbors1.add(pair1)
            }
        }

        for (pair1 in neighbors1) {
            val neighbors2 = ArrayList<Pair>()
            for(pair2 in pairs) {
                if(
                    pair2.slot1 == pair1.slot2 && pair2.slot2 == slot1 ||
                    pair2.slot2 == pair1.slot2 && pair2.slot1 == slot1) {
                    neighbors2.add(pair2)
                }
            }

            if (neighbors2.size > 0) {
                val p3 = neighbors2[0]
                val slot3 = if(p3.slot1 == slot1) p3.slot2 else p3.slot1
                crosses.add(Cross(slot1, slot2, slot3))
            }
        }
    }

    private fun getNeighbors(slot: Slot): ArrayList<Slot> {
        val neighbors = ArrayList<Slot>()
        for(n in slots) {
            if(
                slot.x == n.x && Math.abs(slot.y - n.y) == 1 ||
                slot.y == n.y && Math.abs(slot.x - n.x) == 1 ||
                slot.z == n.z && Math.abs(slot.x - n.x) == 1)
                neighbors.add(n)
        }
        return neighbors
    }
}
