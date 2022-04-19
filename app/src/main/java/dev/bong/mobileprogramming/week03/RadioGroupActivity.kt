package dev.bong.mobileprogramming.week03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioGroup
import dev.bong.mobileprogramming.R

class RadioGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_group)
        init()
    }

    fun init() {
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val imageView = findViewById<ImageView>(R.id.imageView)

        //라디오버튼 체크 이벤트
        radioGroup.setOnCheckedChangeListener { radioGroup, checkedID ->
            when (checkedID) {
                R.id.radioButton1 -> imageView.setImageResource(R.drawable.img1)
                R.id.radioButton2 -> imageView.setImageResource(R.drawable.img2)
                R.id.radioButton3 -> imageView.setImageResource(R.drawable.img3)
            }
        }
    }
}