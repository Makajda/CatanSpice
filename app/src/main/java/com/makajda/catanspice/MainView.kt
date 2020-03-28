package com.makajda.catanspice

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Canvas
import android.view.View

class MainView(context: Context) : View(context) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val activity = activity
        activity?.onDrawChildView(canvas)
    }

    private val activity: MainActivity?
        get() {
            var context = context
            while (context is ContextWrapper) {
                if (context is MainActivity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }
}
