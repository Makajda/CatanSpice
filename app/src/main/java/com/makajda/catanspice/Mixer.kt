package com.makajda.catanspice

class Mixer {
    private val mixProds = MixProds()
    private val mixJettons = MixJettons()
    private val mixSettlements = MixSettlements()

    fun mix(slots: ArrayList<Slot>, settlements: ArrayList<Settlement>, playersCount: Int) {
        mixProds.mix(slots)
        mixJettons.mix(slots)
        mixSettlements.mix(slots, settlements, playersCount)
    }
}