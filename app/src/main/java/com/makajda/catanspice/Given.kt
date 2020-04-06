package com.makajda.catanspice

import android.graphics.Color
import android.graphics.Point

object Given {
    val edge: Int = 2

    //prod == 0 - it is a desert
    val prodsCount = intArrayOf(1, 4, 4, 4, 3, 3)
    val prodsColor = arrayOf(0xFFF5DEB3, 0xFF006400, 0xFF90EE90, 0xFFFFFF00, 0xFF8B4513, 0xFFADD8E6)
    val jettonsCount = intArrayOf(1, 2, 2, 2, 2, 2, 2, 2, 2, 1)
    val jettonsValue = intArrayOf(2, 3, 4, 5, 6, 8, 9, 10, 11, 12)
    val settlements = intArrayOf(Color.RED, Color.BLUE, -23269, Color.WHITE)
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