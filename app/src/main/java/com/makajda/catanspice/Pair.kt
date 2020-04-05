package com.makajda.catanspice

class Pair(val slot1: Slot?, val slot2: Slot?) {
    fun checkSame(slot1: Slot?, slot2: Slot?) : Boolean {
        return (this.slot1 == slot1 && this.slot2 == slot2
                || this.slot1 == slot2 && this.slot2 == slot1)
    }
}