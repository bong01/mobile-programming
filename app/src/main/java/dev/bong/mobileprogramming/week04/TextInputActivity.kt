package dev.bong.mobileprogramming.week04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dev.bong.mobileprogramming.R

class TextInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_input)
        initLayout()
    }

    private fun initLayout() {
        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val emailText = findViewById<TextInputEditText>(R.id.emailText)

        //텍스트가 바뀔 때
        emailText.addTextChangedListener {
            if (it.toString().contains('@')) {
                textInputLayout.error = null
            } else {
                textInputLayout.error = "이메일 형식이 올바르지 않습니다."
            }
        }
    }
}