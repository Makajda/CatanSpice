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
        radiusEllipse = (radiusHexagon / 2.3).toInt()
        jettonFontSize = (radiusHexagon / 1.7).toInt()
        canvas.drawColor(-0x334334)
    }

    private fun Slot(slot: Slot) {
        val center: Point = getCenter(slot.x, slot.z)
        val points = getPoints(center)
        val path = Path()
        path.moveTo(points[0].x.toFloat(), points[0].y.toFloat())
        for (i in 1 until pointsCount) {
            path.lineTo(points[i].x.toFloat(), points[i].y.toFloat())
        }
        val paint = getPaint(Given.prodsColor.get(slot.prod).toInt())
        canvas!!.drawPath(path, paint)

        if (slot.jetton > 0) Jetton(Integer.toString(slot.jetton), center, slot.prod)

        //Jetton("${slot.x}.${slot.y}.${slot.z}", center)
    }

    private fun Settlement(settlement: Settlement?) {
        if (settlement != null) {
            val cross = settlement.cross
            if (cross != null) {
                val point = getEqPoint(cross)
                if (point != null) {
                    val paint = getPaint(Given.settlements.get(settlement.id))
                    val center = getCenter(point.x, point.y)
                    canvas!!.drawCircle(
                        center.x + shiftX.toFloat(),
                        center.y + shiftY.toFloat(),
                        radiusEllipse.toFloat(),
                        paint
                    )
                }
            }
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

    private fun getPoints(center: Point): ArrayList<Point> {
        center.x += shiftX
        center.y += shiftY
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

    private fun getEqPoint(cross: Cross): Point? {
        val points1 = getPoints(getCenter(cross.slot1.x, cross.slot1.z))
        val points2 = getPoints(getCenter(cross.slot2.x, cross.slot2.z))
        val points3 = getPoints(getCenter(cross.slot3.x, cross.slot3.z))

        for(point1 in points1)
            for(point2 in points2)
                for(point3 in points3)
                    if(
                        point1.x == point2.x && point2.x == point3.x &&
                        point1.y == point2.y && point2.y == point3.y
                    )
                        return point1

        return null
    }

    private fun getCenter(q: Int, r: Int) : Point {
        //Внутренний радиус равен Math.Sqrt(3d) / 2d от внешнего радиуса
        val centerX = q * radiusHexagon * 2.0 * Math.sqrt(3.0) / 2.0 + r * radiusHexagon * Math.sqrt(3.0) / 2.0
        val centerY = r * radiusHexagon * 3.0 / 2.0
        return Point(centerX.toInt(), centerY.toInt())
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
