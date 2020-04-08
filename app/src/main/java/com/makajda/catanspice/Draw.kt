package com.makajda.catanspice

import android.graphics.*

internal class Draw {
    val pointsCount = 6
    private var shiftX = 0
    private var shiftY = 0
    private var radiusEllipse = 0
    private var radiusHexagon = 0
    private var jettonFontSize = 0
    private val jettonTextBounds = Rect()
    private var canvas: Canvas? = null

    fun redraw(map: Map, canvas: Canvas, deltaX: Int, deltaY: Int, playerCount: Int) {
        setCanvas(canvas, deltaX, deltaY)
        for (slot in map.slots) {
            Slot(slot)
        }

        for (slot in map.slots) {
            if (checkSettlementId(slot.settlement, playerCount)) Settlement(slot)
        }
    }

    private fun setCanvas(canvas: Canvas, deltaX: Int, deltaY: Int) {
        this.canvas = canvas
        val width = canvas.width - if (canvas.width < canvas.height) 0 else deltaX
        val height = canvas.height - if (canvas.width < canvas.height) deltaY else 0
        shiftX = width / 2
        shiftY = height / 2
        radiusHexagon = (Math.min(width, height) / (Given.edge * 2.0 + 1.0) / Math.sqrt(3.0)).toInt()
        radiusEllipse = (radiusHexagon / 2.3).toInt()
        jettonFontSize = (radiusHexagon / 1.7).toInt()
        canvas.drawColor(-0x334334)
    }

    private fun Slot(slot: Slot) {
        val center: Point = getCenter(slot.x, slot.z, radiusHexagon)
        center.x += shiftX
        center.y += shiftY
        val points = getPoints(center)
        val path = Path()
        path.moveTo(points[0].x.toFloat(), points[0].y.toFloat())
        for (i in 1 until pointsCount) {
            path.lineTo(points[i].x.toFloat(), points[i].y.toFloat())
        }
        val paint = getPaint(Given.prodsColor.get(if(slot.prod > Given.clearValue) slot.prod else 0).toInt())
        canvas!!.drawPath(path, paint)

        if (slot.jetton > 0) Jetton(Integer.toString(slot.jetton), center)

        //Jetton("${slot.x}.${slot.y}.${slot.z}", center)
    }

    private fun Settlement(slot: Slot) {
        val paint = getPaint(Given.settlementsColor.get(slot.settlement))
        val center: Point =
            getCenter(
                slot.x,
                slot.z,
                radiusHexagon,
                slot.isUp
            )
        canvas!!.drawCircle(
            (center.x + shiftX).toFloat(),
            (center.y + shiftY).toFloat(),
            radiusEllipse.toFloat(),
            paint
        )
    }

    private fun Jetton(jetton: String, center: Point) {
        val paint = Paint()
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        if (jetton == "6" || jetton == "8") {
            paint.color = Color.RED
            paint.textSize = jettonFontSize * 1.3f
        } else {
            paint.color = Color.BLACK
            paint.textSize = jettonFontSize.toFloat()
        }

        paint.getTextBounds(jetton, 0, jetton.length, jettonTextBounds)
        canvas!!.drawText(
            jetton,
            center.x - jettonTextBounds.exactCenterX(),
            center.y - jettonTextBounds.exactCenterY(),
            paint
        )
    }

    private fun getPoints(center: Point): ArrayList<Point> {
        val points = ArrayList<Point>()
        for (i in 0 until pointsCount) {
            val a = 60 * i + 30.toDouble()
            val r = a * Math.PI / 180
            points.add(Point(
                center.x + (radiusHexagon * Math.cos(r)).toInt(),
                center.y + (radiusHexagon * Math.sin(r)).toInt()
            ))
        }
        return points
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
