package dev.bong.mobileprogramming.week09

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.bong.mobileprogramming.databinding.ActivityFragmentBinding

class FragmentActivity : AppCompatActivity() {

    lateinit var binding : ActivityFragmentBinding
    val myViewModel : MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}