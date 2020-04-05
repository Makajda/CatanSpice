package com.makajda.catanspice

class MixJettons : MixBase() {
    fun mix(slots: ArrayList<Slot>) {
        mix(slots, Given.jettonsCount)
    }
    override fun setValue(slot: Slot) {
        if(slot.prod > 0)
            slot.jetton = Given.jettonsValue.get(getItem())
        else
            slot.jetton = 0
    }
    override fun checkRest(slot1: Slot, slot2: Slot): Boolean =
        slot1.jetton == slot2.jetton ||
                slot1.jetton == 6 && slot2.jetton == 8 ||
                slot1.jetton == 8 && slot2.jetton == 6
}
