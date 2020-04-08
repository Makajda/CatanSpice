package com.makajda.catanspice

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object MainState {
    private val savedNamePlayersCount = "PlayersCount"
    private val savedNameSlots = "Slots"

    fun fromState(savedInstanceState: Bundle?, map: Map) : Int {
        var playersCount = 3
        if (savedInstanceState != null) {
            try {
                playersCount = savedInstanceState.getInt(savedNamePlayersCount)
                val slotsType: Type = object : TypeToken<ArrayList<Slot>?>() {}.type
                val slots: ArrayList<Slot>? = Gson()
                    .fromJson(savedInstanceState.getString(savedNameSlots), slotsType)
                map.copyFrom(slots)
            }
            catch (e: Exception) { }
        }
        return  playersCount
    }

    fun toState(savedInstanceState: Bundle?, slots: ArrayList<Slot>, playersCount: Int) {
        if (savedInstanceState != null) {
            savedInstanceState.putInt(savedNamePlayersCount, playersCount)
            val slotsJson = Gson().toJson(slots)
            savedInstanceState.putString(savedNameSlots, slotsJson)
        }
    }
}