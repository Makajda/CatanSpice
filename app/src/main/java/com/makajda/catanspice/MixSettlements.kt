package com.makajda.catanspice

import android.graphics.Point
import kotlin.random.Random
import kotlin.collections.ArrayList

class MixSettlements {
    private val radius = 30 //only for validate distance

    fun mix(slots: ArrayList<Slot>, settlements: ArrayList<Settlement>, playersCount: Int) {
        val is3 = playersCount == 3
        val slotsCount = slots.size

        for (settlement in settlements) {
            settlement.slot = null
        }

        for (k in 0..1) {
            for (i in 0 until playersCount) {
                val settlement = settlements[i * 2 + k]
                do {
                    settlement.slot = slots[Random.nextInt(slotsCount)]
                    settlement.isUp = Random.nextBoolean()
                } while (!validate(settlement, slots, settlements, is3, k == 0))
            }
        }
    }

    private fun validate(
        settlement: Settlement,
        slots: ArrayList<Slot>,
        settlements: ArrayList<Settlement>,
        is3: Boolean,
        needGoodJetton: Boolean
    ): Boolean {
        val slot = settlement.slot
        if(slot != null) {
            //desert
            if (is3 && slot.prod == 0) {
                return false
            }

            //desert & edge
            val one = if (settlement.isUp) 1 else -1
            val (isEdgeA, gj1) = checkNeighbor(slots, slot, 0, one, is3)
            if(!isEdgeA) {
                return false
            }
            val (isEdgeB, gj2) = checkNeighbor(slots, slot, one, one, is3)
            if(!isEdgeB) {
                return false
            }

            //6-8
            val gj3 = goodJetton(slot)
            if(needGoodJetton) {
                if(!gj1 && !gj2 && !gj3) {
                    return false
                }
            }
            else {
                if(gj1 || gj2 || gj3) {
                    return false
                }
            }

            // Validate Distance
            val center: Point = getCenter(slot.x, slot.z, radius, settlement.isUp)
            for (settlementRest in settlements) {
                if (settlementRest !== settlement) {
                    val slotRest = settlementRest.slot
                    if (slotRest != null) {
                        val centerRest: Point = getCenter(
                            slotRest.x,
                            slotRest.z,
                            radius,
                            settlementRest.isUp
                        )
                        val distance = Math.sqrt(
                            Math.pow(center.x - centerRest.x.toDouble(), 2.0) +
                                    Math.pow(center.y - centerRest.y.toDouble(), 2.0)
                        ).toInt()
                        if (distance < radius + 1) {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }

    private fun checkNeighbor(
        slots: ArrayList<Slot>,
        slot: Slot,
        x: Int,
        z: Int,
        is3: Boolean
    ): Pair<Boolean, Boolean> {
        for (n in slots) {
            if (n.x == slot.x + x && n.z == slot.z - z) {
                return Pair(!is3 || n.prod > 0, goodJetton(n))
            }
        }

        return Pair(false, false)
    }

    private fun goodJetton(slot: Slot) : Boolean {
        val jetton = slot.jetton
        return jetton == 6 || jetton == 8
    }
}