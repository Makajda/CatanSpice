package com.makajda.catanspice

import kotlin.random.Random

abstract class MixBase {
    private val items = mutableListOf<Int>()

    protected abstract fun getValue(slot: Slot) : Int
    protected abstract fun setValue(slot: Slot)
    protected abstract fun clearValue(slot: Slot)

    protected open fun checkRest(slot1: Slot, slot2: Slot) : Boolean = true

    protected fun mix(slots: ArrayList<Slot>, counts: IntArray) {
        do {
            for (slot in slots) {
                clearValue(slot)
            }
            createItems(counts)

            var isValidated = true

            for (slot in slots) {
                setValue(slot)
                isValidated = validate(slot, slots)
                if(!isValidated) {
                    break
                }
            }
        } while (!isValidated)
    }

    private fun createItems(counts: IntArray) {
        items.clear()
        for ((index, p) in counts.withIndex()) {
            for (i in 0 until p) {
                items.add(index)
            }
        }
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

    protected open fun validate(slot: Slot, slots: ArrayList<Slot>): Boolean {
        //проверить есть ли соседние одинаковые, смотрим вокруг
        for (n in slots) {
            if (getValue(n) > Given.clearValue &&
                (slot.x == n.x && Math.abs(slot.y - n.y) == 1 ||
                        slot.y == n.y && Math.abs(slot.x - n.x) == 1 ||
                        slot.z == n.z && Math.abs(slot.x - n.x) == 1)
                && checkRest(slot, n)
            ) return false
        }

        return true
    }
}