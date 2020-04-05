package com.makajda.catanspice

import kotlin.random.Random

abstract class MixBase {
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