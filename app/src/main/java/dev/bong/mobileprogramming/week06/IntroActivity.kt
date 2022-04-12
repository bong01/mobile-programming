package dev.bong.mobileprogramming.week06

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.ActivityIntroBinding
import dev.bong.mobileprogramming.week05.VocabActivity
import dev.bong.mobileprogramming.week05.VocabData

class IntroActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntroBinding
    val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val voc = it.data?.getSerializableExtra("voc") as VocabData
            Toast.makeText(this, voc.word + " 단어가 추가됨", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.button.setOnClickListener {
            val intent = Intent(this, VocabActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this, AddVocActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }
}