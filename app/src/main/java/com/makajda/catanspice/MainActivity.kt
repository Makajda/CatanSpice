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
    private val savedNamePlayersCount = "PlayersCount"
    private var playersCount = 3
    private var map = Map()
    private val mixProds = MixProds()
    private val mixJettons = MixJettons()
    private val mixSettlements = MixSettlements()
    private var draw: Draw = Draw()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (savedInstanceState != null) {
            playersCount = savedInstanceState.getInt(savedNamePlayersCount)
        }

        addViewAndButtons()
        mixAll()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(savedNamePlayersCount, playersCount)
    }

    fun onDrawChildView(canvas: Canvas) {
        val controlsLayout = findViewById<LinearLayout>(controlsLayoutId)
        draw.redraw(map, canvas, controlsLayout.width, controlsLayout.height)
    }

    private fun addViewAndButtons() {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val isVertical = size.x < size.y;

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

        controlsLayout.addView(getButton(R.drawable.ic_action_mix_jettons) { mixJettons(); view.invalidate() })
        controlsLayout.addView(getButton(R.drawable.ic_action_mix3) { mixSettlements(3); view.invalidate() })
        controlsLayout.addView(getButton(R.drawable.ic_action_mix4) { mixSettlements(4); view.invalidate() })
        controlsLayout.addView(getButton(R.drawable.ic_action_mix_all) { mixAll(); view.invalidate() })
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

    private fun mixJettons() {
        mixJettons.mix(map.slots)
        mixSettlements.mix(map.slots, map.settlements, playersCount)
    }

    private fun mixSettlements(playersCount: Int) {
        this.playersCount = playersCount
        mixSettlements.mix(map.slots, map.settlements, playersCount)
    }

    private fun mixAll() {
        mixProds.mix(map.slots)
        mixJettons.mix(map.slots)
        mixSettlements.mix(map.slots, map.settlements, playersCount)
    }
}
