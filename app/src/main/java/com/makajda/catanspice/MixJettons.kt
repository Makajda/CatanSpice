package com.makajda.catanspice

class MixJettons : MixBase() {
    fun mix(slots: ArrayList<Slot>) {
        mix(slots, Given.jettonsCount)
    }

    override fun clearValue(slot: Slot) { slot.jetton = Given.clearValue }

    override fun setAndValidate(slot: Slot, slots: ArrayList<Slot>): Boolean {
        val jetton1 =  if(slot.prod > 0) Given.jettonsValue.get(getItem()) else Given.clearValue
        slot.jetton = jetton1

        //проверить есть ли установленные соседние одинаковые, смотрим вокруг
        for (n in slots) {
            val jetton2 = n.jetton
            if (jetton2 > Given.clearValue &&
                (jetton1 == jetton2 ||
                        jetton1 == 6 && jetton2 == 8 ||
                        jetton1 == 8 && jetton2 == 6) &&
                (slot.x == n.x && Math.abs(slot.y - n.y) == 1 ||
                        slot.y == n.y && Math.abs(slot.x - n.x) == 1 ||
                        slot.z == n.z && Math.abs(slot.x - n.x) == 1)
            ) return false
        }

        return true
    }
}
