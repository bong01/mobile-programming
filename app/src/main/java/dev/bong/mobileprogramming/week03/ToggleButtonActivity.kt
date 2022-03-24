package dev.bong.mobileprogramming.week03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import dev.bong.mobileprogramming.R

class ToggleButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toggle_button)
        init()
    }

    fun init() {
        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        toggleButton.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Toggle On", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Toggle Off", Toast.LENGTH_SHORT).show()
            }
        }
    }
}