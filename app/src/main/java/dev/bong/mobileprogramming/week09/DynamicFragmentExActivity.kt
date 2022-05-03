package dev.bong.mobileprogramming.week09

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.ActivityDynamicFragmentExBinding

class DynamicFragmentExActivity : AppCompatActivity() {

    lateinit var binding: ActivityDynamicFragmentExBinding
    val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDynamicFragmentExBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        val fragment = supportFragmentManager.beginTransaction()
        val imgFragment = DynamicExImageFragment()
        fragment.replace(R.id.frameLayout, imgFragment)
        fragment.commit()
    }
}