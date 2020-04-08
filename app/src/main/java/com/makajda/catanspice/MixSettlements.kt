package com.makajda.catanspice

import android.graphics.Point
import kotlin.random.Random
import kotlin.collections.ArrayList

class MixSettlements : MixBase() {
    private val radius = 30 //only for validate distance
    private var playersCount = 3

    fun mix(slots: ArrayList<Slot>, playersCount: Int) {
        val playersCount2 = playersCount * 2
        val counts = IntArray(playersCount2 + 1) { 1 }
        counts[playersCount2] = slots.size - playersCount2
        this.playersCount = playersCount
        mix(slots, counts)
    }

    override fun clearValue(slot: Slot) { slot.settlement = Given.clearValue }

    override fun setAndValidate(slot: Slot, slots: ArrayList<Slot>): Boolean {
        val settlement = getItem()
        val isUp = Random.nextBoolean()
        if (!checkSettlementId(settlement, playersCount)) {
            return true
        }

        //desert
        if (playersCount == 3 && slot.prod == 0) {
            return false
        }

        //desert & edge
        val one = if (isUp) 1 else -1
        val (notEdgeA, gj1) = checkNeighbor(slot, slots, 0, one)
        if (!notEdgeA) {
            return false
        }
        val (notEdgeB, gj2) = checkNeighbor(slot, slots, one, one)
        if (!notEdgeB) {
            return false
        }

        //6-8
        if (settlement % 2 == 0) {
            val gj3 = goodJetton(slot.jetton)
            if (!gj1 && !gj2 && !gj3) {
                return false
            }
        }
//        else {
//            if(gj1 || gj2 || gj3) {
//                return false
//            }
//        }

        // Distance
        val center: Point = getCenter(slot.x, slot.z, radius, isUp)
        for (slotRest in slots) {
            if (slotRest !== slot) {
                if (checkSettlementId(slotRest.settlement, playersCount)) {
                    val centerRest: Point = getCenter(slotRest.x, slotRest.z, radius, slotRest.isUp)
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

        slot.settlement = settlement / 2
        slot.isUp = isUp
        return true
    }

    private fun checkNeighbor(
        slot: Slot,
        slots: ArrayList<Slot>,
        x: Int,
        z: Int
    ): Pair<Boolean, Boolean> {
        for (n in slots) {
            if (n.x == slot.x + x && n.z == slot.z - z) {
                return Pair(playersCount != 3 || n.prod > 0, goodJetton(n.jetton))
            }
        }

        return Pair(false, false)
    }

    private fun goodJetton(jetton: Int) : Boolean {
        return jetton == 6 || jetton == 8
    }
}