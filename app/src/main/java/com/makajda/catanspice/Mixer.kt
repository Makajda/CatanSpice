package com.makajda.catanspice

class Mixer {
    private val mixProds = MixProds()
    private val mixJettons = MixJettons()
    private val mixSettlements = MixSettlements()

    fun mix(slots: ArrayList<Slot>, playersCount: Int) {
        mixProds.mix(slots)
        mixJettons.mix(slots)
        mixSettlements.mix(slots, playersCount)
    }
}