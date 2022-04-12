package dev.bong.mobileprogramming.week06

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.ActivityAddVocBinding
import dev.bong.mobileprogramming.databinding.ActivityIntroBinding
import dev.bong.mobileprogramming.week05.VocabData
import java.io.PrintStream

class AddVocActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddVocBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVocBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            button3.setOnClickListener {
                //추가 버튼
                val word = word.text.toString()
                val meaning = meaning.text.toString()
                writeFile(word, meaning)
            }

            button4.setOnClickListener {
                //취소 버튼
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

    private fun writeFile(word: String, meaning: String) {
        val output = PrintStream(openFileOutput("out.txt", Context.MODE_APPEND))
        output.println(word)
        output.println(meaning)
        output.close()

        val intent = Intent()
        intent.putExtra("voc", VocabData(word, meaning))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}