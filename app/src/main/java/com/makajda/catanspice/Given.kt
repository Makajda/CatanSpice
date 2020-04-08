package com.makajda.catanspice

import android.graphics.Color
import android.graphics.Point

object Given {
    val edge: Int = 2

    //prod == 0 - it is a desert
    val prodsCount = intArrayOf(1, 4, 4, 4, 3, 3)
    val prodsColor = arrayOf(0xFFF5DEB3, 0xFF008000, 0xFFA0EDA0, 0xFFFFFF00, 0xFFA86A13, 0xFF6FCAE7)
    val jettonsCount = intArrayOf(1, 2, 2, 2, 2, 2, 2, 2, 2, 1)
    val jettonsValue = intArrayOf(2, 3, 4, 5, 6, 8, 9, 10, 11, 12)
    val settlementsColor = intArrayOf(Color.RED, Color.BLUE, -23269, Color.WHITE) //-23269 = 0xFFA500
    val clearValue = -1 // < 0
}

fun getCenter(q: Int, r: Int, radius: Int) : Point {
    //Внутренний радиус равен Math.Sqrt(3d) / 2d от внешнего радиуса
    val centerX = q * radius * 2.0 * Math.sqrt(3.0) / 2.0 + r * radius * Math.sqrt(3.0) / 2.0
    val centerY = r * radius * 3.0 / 2.0
    return Point(centerX.toInt(), centerY.toInt())
}

fun getCenter(q: Int, r: Int, radius: Int, isUp: Boolean) : Point {
    val c = getCenter(q, r, radius)
    return Point(c.x, c.y  + if(isUp) -radius else radius)
}

fun checkSettlementId(settlement: Int, playersCount: Int) : Boolean {
    return settlement > Given.clearValue && settlement < playersCount * 2
}
