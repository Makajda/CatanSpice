package com.makajda.catanspice

import android.graphics.*

internal class Draw {
    private var shiftX = 0
    private var shiftY = 0
    private var radiusEllipse = 0
    private var radiusHexagon = 0
    private var jettonFontSize = 0
    private val jettonTextBounds = Rect()
    private var canvas: Canvas? = null

    fun redraw(map: Map, canvas: Canvas, deltaX: Int, deltaY: Int) {
        setCanvas(canvas, deltaX, deltaY)
        for (slot in map.slots) {
            Slot(slot)
        }
        for (settlement in map.settlements) {
            Settlement(settlement)
        }
    }

    private fun setCanvas(canvas: Canvas, deltaX: Int, deltaY: Int) {
        this.canvas = canvas
        val width = canvas.width - if (canvas.width < canvas.height) 0 else deltaX
        val height = canvas.height - if (canvas.width < canvas.height) deltaY else 0
        shiftX = width / 2
        shiftY = height / 2
        radiusHexagon = (Math.min(width, height) / (Given.edge * 2.0 + 1.0) / Math.sqrt(3.0)).toInt()
        radiusEllipse = (radiusHexagon / 2.7).toInt()
        jettonFontSize = (radiusHexagon / 1.7).toInt()
        canvas.drawColor(-0x334334)
    }

    private fun Slot(slot: Slot) {
        val pointCount = 6
        val center: Point = getCenter(slot.x, slot.z, radiusHexagon)
        center.x += shiftX
        center.y += shiftY
        val points =
            arrayOfNulls<Point>(pointCount)
        for (i in 0 until pointCount) {
            val a = 60 * i + 30.toDouble()
            val r = a * Math.PI / 180
            points[i] = Point(
                center.x + (radiusHexagon * Math.cos(r)).toInt(),
                center.y + (radiusHexagon * Math.sin(r)).toInt()
            )
        }
        val path = Path()
        path.moveTo(points[0]!!.x.toFloat(), points[0]!!.y.toFloat())
        for (i in 1 until pointCount) {
            path.lineTo(points[i]!!.x.toFloat(), points[i]!!.y.toFloat())
        }
        val paint = getPaint(Given.prodsColor.get(slot.prod).toInt())
        canvas!!.drawPath(path, paint)

        if (slot.jetton > 0) Jetton(Integer.toString(slot.jetton), center, slot.prod)

        //Jetton("${slot.x}.${slot.y}.${slot.z}", center)
    }

    private fun Settlement(settlement: Settlement?) {
        if (settlement != null && settlement.slot != null) {
            val paint = getPaint(Given.settlements.get(settlement.id))
            val center: Point =
                getCenter(
                    settlement.slot!!.x,
                    settlement.slot!!.z,
                    radiusHexagon,
                    settlement.isUp
                )
            canvas!!.drawCircle(
                center.x + shiftX.toFloat(),
                center.y + shiftY.toFloat(),
                radiusEllipse.toFloat(),
                paint
            )
        }
    }

    private fun Jetton(jetton: String, center: Point, prod: Int) {
        val paint = Paint()
        paint.isFakeBoldText = true
        paint.textSize = jettonFontSize.toFloat()
        if (jetton == "6" || jetton == "8") {
            paint.color = Color.RED
        } else {
            paint.color = if(prod == 3) Color.BLACK else Color.WHITE
        }

        paint.getTextBounds(jetton, 0, jetton.length, jettonTextBounds)
        canvas!!.drawText(
            jetton,
            center.x - jettonTextBounds.exactCenterX(),
            center.y - jettonTextBounds.exactCenterY(),
            paint
        )
    }

    companion object {
        private fun getPaint(color: Int): Paint {
            val paint = Paint()
            paint.style = Paint.Style.FILL
            paint.color = color
            return paint
        }
    }
}
