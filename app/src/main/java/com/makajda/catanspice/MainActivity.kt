package com.makajda.catanspice

import android.graphics.Canvas
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {
    private val controlsLayoutId = 1
    private var playersCount = 3
    private val map = Map()
    private val mixer = Mixer()
    private val draw: Draw = Draw()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        playersCount = MainState.fromState(savedInstanceState, map)
        addViewAndButtons()

        if(map.slots.size == 0 || map.settlements.size == 0) {
            map.create()
            mixer.mix(map.slots, map.settlements, playersCount)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        MainState.toState(savedInstanceState, map.slots, map.settlements, playersCount)
    }

    fun onDrawChildView(canvas: Canvas) {
        val controlsLayout = findViewById<LinearLayout>(controlsLayoutId)
        draw.redraw(map, canvas, controlsLayout.width, controlsLayout.height)
    }

    private fun addViewAndButtons() {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val isVertical = size.x < size.y

        val mainLayout = RelativeLayout(this)
        setContentView(mainLayout)

        val view = MainView(this)
        mainLayout.addView(view)

        val controlsLayout = LinearLayout(this)
        controlsLayout.id = controlsLayoutId

        if(isVertical) {
            val controlsLayoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            controlsLayout.orientation = LinearLayout.HORIZONTAL
            controlsLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            mainLayout.addView(controlsLayout, controlsLayoutParams)
        }
        else {
            val controlsLayoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            controlsLayout.orientation = LinearLayout.VERTICAL
            controlsLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            mainLayout.addView(controlsLayout, controlsLayoutParams)
        }

        controlsLayout.addView(getButton(R.drawable.ic_mix4) { mix(4, view); })
        controlsLayout.addView(getButton(R.drawable.ic_mix3) { mix(3, view); })
    }

    private fun getButton(resId: Int, onClick: (View) -> Unit): ImageButton {
        val button = ImageButton(this)
        button.setImageResource(resId)
        button.scaleType = ImageView.ScaleType.FIT_XY
        val buttonParams = LinearLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        buttonParams.setMargins(-7, -11, -7, -11)
        button.layoutParams = buttonParams
        val scale = resources.displayMetrics.density
        val dp = (30 * scale + 0.5f).toInt()
        button.setPadding(dp, dp, dp, dp)
        button.setOnClickListener(onClick)
        return button
    }

    private fun mix(playersCount: Int, view: MainView) {
        this.playersCount = playersCount
        mixer.mix(map.slots, map.settlements, playersCount)
        view.invalidate()
    }
}
