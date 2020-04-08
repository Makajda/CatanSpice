package com.makajda.catanspice

class MixProds : MixBase() {
    fun mix(slots: ArrayList<Slot>) {
        mix(slots, Given.prodsCount)
    }

    override fun clearValue(slot: Slot) { slot.prod = Given.clearValue }

    override fun setAndValidate(slot: Slot, slots: ArrayList<Slot>): Boolean {
        slot.prod = getItem()

        //проверить есть ли установленные соседние одинаковые, смотрим вокруг
        for (n in slots) {
            if (n.prod > Given.clearValue && slot.prod == n.prod &&
                (slot.x == n.x && Math.abs(slot.y - n.y) == 1 ||
                        slot.y == n.y && Math.abs(slot.x - n.x) == 1 ||
                        slot.z == n.z && Math.abs(slot.x - n.x) == 1)

            ) return false
        }

        return true
    }
}
