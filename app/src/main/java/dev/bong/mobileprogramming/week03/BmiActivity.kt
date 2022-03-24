package dev.bong.mobileprogramming.week03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import dev.bong.mobileprogramming.R
import kotlin.math.pow

class BmiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        init()
    }

    fun init() {
        val bmiButton = findViewById<Button>(R.id.bmiButton)
        val weight = findViewById<EditText>(R.id.weight)
        val height = findViewById<EditText>(R.id.height)
        val imageView = findViewById<ImageView>(R.id.imageView)

        bmiButton.setOnClickListener{
            val bmi = weight.text.toString().toDouble() / (height.text.toString().toDouble() / 100).pow(2.0)
            var resultString:String
            when{
                bmi >= 35 -> {
                    resultString = "고도 비만"
                    imageView.setImageResource(R.drawable.ic_baseline_battery_full_24)
                }
                bmi >= 23 -> {
                    resultString = "과체중"
                    imageView.setImageResource(R.drawable.ic_baseline_battery_6_bar_24)
                }
                bmi >= 18.5 -> {
                    resultString = "정상"
                    imageView.setImageResource(R.drawable.ic_baseline_battery_4_bar_24)
                }
                else -> {
                    resultString = "저체중"
                    imageView.setImageResource(R.drawable.ic_baseline_battery_0_bar_24)
                }
            }
            Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show()
        }
    }
}