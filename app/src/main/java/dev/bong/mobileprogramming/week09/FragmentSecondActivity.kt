package dev.bong.mobileprogramming.week09

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.bong.mobileprogramming.databinding.ActivityFragmentSecondBinding

class FragmentSecondActivity : AppCompatActivity() {

    lateinit var binding: ActivityFragmentSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}