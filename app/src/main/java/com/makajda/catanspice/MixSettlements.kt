package com.makajda.catanspice

import android.graphics.Point
import kotlin.random.Random
import kotlin.collections.ArrayList

class MixSettlements {
    private val crosses = ArrayList<Cross>()

    fun mix(slots: ArrayList<Slot>, settlements: ArrayList<Settlement>, playersCount: Int) {
        val is3 = playersCount == 3
        for (settlement in settlements) {
            settlement.cross = null
        }

        Crosses.addCrosses(crosses, slots)

        do {
            for (i in 0 until playersCount) {
                val settlement1 = settlements[i * 2]
                val settlement2 = settlements[i * 2 + 1]
                do {
                    val cross1 = crosses[Random.nextInt(crosses.size)]
                    val cross2 = crosses[Random.nextInt(crosses.size)]
                    settlement1.cross = cross1
                    settlement2.cross = cross2
                } while(
                    !cross1.checkJetton68() &&
                    !cross2.checkJetton68() &&
                    (is3 && (cross1.checkProd0() || cross2.checkProd0()))
                )
            }
        } while (!validate(settlements))
    }

    private fun validate(settlements: ArrayList<Settlement>): Boolean {
        // Validate Distance
        return settlements.size > 0
    }
}