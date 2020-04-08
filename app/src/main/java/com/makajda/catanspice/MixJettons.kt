package com.makajda.catanspice

class MixJettons : MixBase() {
    fun mix(slots: ArrayList<Slot>) {
        mix(slots, Given.jettonsCount)
    }
    override fun getValue(slot: Slot) : Int { return slot.jetton }
    override fun setValue(slot: Slot) {
        if(slot.prod > 0)
            slot.jetton = Given.jettonsValue.get(getItem())
        else
            slot.jetton = Given.clearValue
    }
    override fun clearValue(slot: Slot) { slot.jetton = Given.clearValue }
    override fun checkRest(slot1: Slot, slot2: Slot): Boolean =
        slot1.jetton == slot2.jetton ||
                slot1.jetton == 6 && slot2.jetton == 8 ||
                slot1.jetton == 8 && slot2.jetton == 6
}
