package com.makajda.catanspice

import android.graphics.Point
import kotlin.random.Random
import kotlin.collections.ArrayList

class MixSettlements {
    private val radius = 30 //only for validate distance

    fun mix(slots: ArrayList<Slot>, settlements: ArrayList<Settlement>, playersCount: Int) {
        for (settlement in settlements) {
            settlement.slot = null
        }

        val slotsCount = slots.size
        for (i in 0 until playersCount * 2) {
            val settlement = settlements[i]
            do {
                settlement.slot = slots[Random.nextInt(slotsCount)]
                settlement.isUp = Random.nextBoolean()
            } while (!validate(settlement, slots, settlements, playersCount == 3))
        }
    }

    private fun validate(
        settlement: Settlement,
        slots: ArrayList<Slot>,
        settlements: ArrayList<Settlement>,
        is3: Boolean
    ): Boolean {
        //desert && edge
        if (is3 && settlement.slot!!.prod == 0) {
            return false
        } else {
            val one = if (settlement.isUp) 1 else -1
            if(!checkNeighbor(slots, settlement.slot!!, 0, one, is3))
                return false
            if(!checkNeighbor(slots, settlement.slot!!, one, one, is3))
                return false
        }

        // Validate Distance
        val center: Point = getCenter(
            settlement.slot!!.x, settlement.slot!!.z, radius, settlement.isUp)

        for (settlementRest in settlements) {
            if (settlementRest !== settlement && settlementRest.slot != null) {
                val centerRest: Point = getCenter(
                    settlementRest.slot!!.x,
                    settlementRest.slot!!.z,
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
        return true
    }

    private fun checkNeighbor(
        slots: ArrayList<Slot>,
        slot: Slot,
        x: Int,
        z: Int,
        is3: Boolean
    ): Boolean {
        for (n in slots) {
            if (n.x == slot.x + x && n.z == slot.z - z) {
                return !is3 || n.prod > 0
            }
        }

        return false
    }
}