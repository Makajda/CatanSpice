package com.makajda.catanspice

class Cross(val slot1: Slot, val slot2: Slot, val slot3: Slot) {
    fun checkJetton68() : Boolean {
        return slot1.jetton == 6 || slot2.jetton == 6 || slot3.jetton == 6 ||
            slot1.jetton == 8 || slot2.jetton == 8 || slot3.jetton == 8
    }

    //desert
    fun checkProd0() : Boolean {
        return slot1.prod == 0 || slot2.prod == 0 || slot3.prod == 0
    }
}

object Crosses {
    fun addCrosses(crosses:  ArrayList<Cross>, slots: ArrayList<Slot>) {
        val pairs = createPairs(slots)
        while (pairs.size > 0) {
            val pair = pairs[0]
            pairs.remove(pair)
            addCross(crosses, pairs, pair.slot1, pair.slot2)
            addCross(crosses, pairs, pair.slot2, pair.slot1)
        }
    }

    private fun createPairs(slots: ArrayList<Slot>): ArrayList<Pair> {
        val pairs = ArrayList<Pair>()
        for (slot in slots) {
            val neighbors = getNeighbors(slot, slots)
            for (neighbor in neighbors) {
                var exist = false
                for (pair in pairs) {
                    if (pair.checkSame(slot, neighbor)) {
                        exist = true
                        break
                    }
                }
                if (!exist) {
                    pairs.add(Pair(slot, neighbor))
                }
            }
        }
        return pairs
    }

    private fun addCross(crosses: ArrayList<Cross>, pairs: ArrayList<Pair>, slot1: Slot, slot2: Slot) {
        val neighbors1 = ArrayList<Pair>()
        for (pair1 in pairs) {
            if (pair1.slot1 == slot2) {
                neighbors1.add(pair1)
            }
        }

        for (pair1 in neighbors1) {
            val neighbors2 = ArrayList<Pair>()
            for (pair2 in pairs) {
                if (
                    pair2.slot1 == pair1.slot2 && pair2.slot2 == slot1 ||
                    pair2.slot2 == pair1.slot2 && pair2.slot1 == slot1
                ) {
                    neighbors2.add(pair2)
                }
            }

            if (neighbors2.size > 0) {
                val p3 = neighbors2[0]
                val slot3 = if (p3.slot1 == slot1) p3.slot2 else p3.slot1
                crosses.add(Cross(slot1, slot2, slot3))
            }
        }
    }

    private fun getNeighbors(slot: Slot, slots: ArrayList<Slot>): ArrayList<Slot> {
        val neighbors = ArrayList<Slot>()
        for (n in slots) {
            if (
                slot.x == n.x && Math.abs(slot.y - n.y) == 1 ||
                slot.y == n.y && Math.abs(slot.x - n.x) == 1 ||
                slot.z == n.z && Math.abs(slot.x - n.x) == 1
            )
                neighbors.add(n)
        }
        return neighbors
    }
}