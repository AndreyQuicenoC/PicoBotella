package com.example.picobotella.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import com.example.picobotella.R

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val btnRate: ImageButton
    private val btnAudioToggle: ImageButton
    private val btnInstructions: ImageButton
    private val btnChallenges: ImageButton
    private val btnShare: ImageButton

    var onRateClick: (() -> Unit)? = null
    var onAudioToggleClick: (() -> Unit)? = null
    var onInstructionsClick: (() -> Unit)? = null
    var onChallengesClick: (() -> Unit)? = null
    var onShareClick: (() -> Unit)? = null

    init {
        orientation = HORIZONTAL
        gravity = android.view.Gravity.CENTER
        setBackgroundResource(R.drawable.bg_custom_toolbar)
        val padding = resources.getDimensionPixelSize(R.dimen.toolbar_padding)
        setPadding(padding, padding, padding, padding)

        LayoutInflater.from(context).inflate(R.layout.view_custom_toolbar, this, true)

        btnRate = findViewById(R.id.btnRate)
        btnAudioToggle = findViewById(R.id.btnAudioToggle)
        btnInstructions = findViewById(R.id.btnInstructions)
        btnChallenges = findViewById(R.id.btnChallenges)
        btnShare = findViewById(R.id.btnShare)

        setupButton(btnRate) { onRateClick?.invoke() }
        setupButton(btnAudioToggle) { onAudioToggleClick?.invoke() }
        setupButton(btnInstructions) { onInstructionsClick?.invoke() }
        setupButton(btnChallenges) { onChallengesClick?.invoke() }
        setupButton(btnShare) { onShareClick?.invoke() }
    }

    fun setAudioOn(isOn: Boolean) {
        val icon = if (isOn) R.drawable.ic_audio_on else R.drawable.ic_audio_off
        btnAudioToggle.setImageResource(icon)
        btnAudioToggle.contentDescription = context.getString(
            if (isOn) R.string.desc_audio_on else R.string.desc_audio_off
        )
    }

    private fun setupButton(button: View, onClick: () -> Unit) {
        button.setOnClickListener { view ->
            applyTouchAnimation(view) { onClick() }
        }
    }

    private fun applyTouchAnimation(view: View, onAnimationEnd: () -> Unit) {
        view.animate()
            .scaleX(0.85f)
            .scaleY(0.85f)
            .setDuration(80)
            .withEndAction {
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(80)
                    .withEndAction { onAnimationEnd() }
                    .start()
            }
            .start()
    }
}
