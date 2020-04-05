package com.makajda.catanspice

class MixProds : MixBase() {
    fun mix(slots: ArrayList<Slot>) {
        mix(slots, Given.prodsCount)
    }
    override fun setValue(slot: Slot) { slot.prod = getItem() }
    override fun checkRest(slot1: Slot, slot2: Slot): Boolean = slot1.prod == slot2.prod
}
