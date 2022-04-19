package dev.bong.mobileprogramming.week04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import dev.bong.mobileprogramming.R

class LayoutDesignActivity : AppCompatActivity() {

    val checkedId = intArrayOf(
        R.id.checkBox1,
        R.id.checkBox2,
        R.id.checkBox3,
        R.id.checkBox4,
        R.id.checkBox5,
        R.id.checkBox6,
        R.id.checkBox7,
        R.id.checkBox8,
        R.id.checkBox9,
        R.id.checkBox10
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_design)
        initLayout()
    }

    private fun initLayout() {
        for (id in checkedId) {
            //체크 박스 정의
            val checkBox = findViewById<CheckBox>(id)

            //체크 박스 체크 이벤트
            checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
                //체크 박스 텍스트에 해당하는 이미지 Id를 res에서 찾기
                val imgId = resources.getIdentifier(compoundButton.text.toString(), "id", packageName)
                //찾은 이미지 Id로 이미지뷰 정의
                val imageView = findViewById<ImageView>(imgId)

                //체크 이벤트
                if (isChecked) {
                    imageView.visibility = View.VISIBLE
                } else {
                    imageView.visibility = View.INVISIBLE
                }
            }
        }
    }
}