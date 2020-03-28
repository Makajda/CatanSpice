package com.makajda.catanspice

import kotlin.random.Random

internal class MixProds : MixSlots() {
    fun mix(slots: ArrayList<Slot>) {
        mix(slots, Given.prodsCount)
    }
    override fun setValue(slot: Slot) { slot.prod = getItem() }
    override fun checkRest(slot1: Slot, slot2: Slot): Boolean = slot1.prod == slot2.prod
}

internal class MixJettons : MixSlots() {
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

internal abstract class MixSlots {
    private val items = mutableListOf<Int>()

    protected abstract fun setValue(slot: Slot)
    protected abstract fun checkRest(slot1: Slot, slot2: Slot) : Boolean

    protected fun mix(slots: ArrayList<Slot>, counts: IntArray) {
        do {
            addItems(counts)
            for (slot in slots) {
                setValue(slot)
            }
        } while (!validate(slots))
    }

    protected fun getItem(): Int {
        val count: Int = items.size
        val p: Int = Random.nextInt(count)
        return if (p < count) {
            val item: Int = items.get(p)
            items.removeAt(p)
            item
        } else {
            0
        }
    }

    private fun validate(slots: ArrayList<Slot>): Boolean {
        //проверить есть ли соседние одинаковые
        for (slot in slots) {
            //смотрим вокруг
            for (n in slots) {
                if (
                    (slot.x == n.x && Math.abs(slot.y - n.y) == 1 ||
                            slot.y == n.y && Math.abs(slot.x - n.x) == 1 ||
                            slot.z == n.z && Math.abs(slot.x - n.x) == 1)
                    && checkRest(slot, n)
                ) return false
            }
        }

        return true
    }

    private fun addItems(counts: IntArray) {
        for ((index, p) in counts.withIndex()) {
            for (i in 0 until p) {
                items.add(index)
            }
        }
    }
}