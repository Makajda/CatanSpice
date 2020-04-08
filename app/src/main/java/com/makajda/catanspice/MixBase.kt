package com.makajda.catanspice

import kotlin.random.Random

abstract class MixBase {
    private val items = mutableListOf<Int>()

    protected abstract fun clearValue(slot: Slot)
    protected abstract fun setAndValidate(slot: Slot, slots: ArrayList<Slot>): Boolean

    protected fun mix(slots: ArrayList<Slot>, counts: IntArray) {
        outer@while(true) {
            for (slot in slots) {
                clearValue(slot)
            }

            createItems(counts)

            for (slot in slots) {
                if(!setAndValidate(slot, slots)) {
                    continue@outer
                }
            }

            break
        }
    }

    protected fun getItem(): Int {
        val count: Int = items.size
        val p: Int = Random.nextInt(count)
        val item: Int = items.get(p)
        items.removeAt(p)
        return item
    }

    private fun createItems(counts: IntArray) {
        items.clear()
        for ((index, p) in counts.withIndex()) {
            for (i in 0 until p) {
                items.add(index)
            }
        }
    }
}