package dev.bong.mobileprogramming.week09

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.ActivityDynamicFragmentBinding

class DynamicFragmentActivity : AppCompatActivity() {

    lateinit var binding: ActivityDynamicFragmentBinding
    val imgFragment = DynamicImageFragment()
    val itemFragment = ItemFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDynamicFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.frameLayout, imgFragment)
        fragment.commit()
        binding.apply {
            button1.setOnClickListener {
                if (!imgFragment.isVisible) {
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.addToBackStack(null)
                    fragment.replace(R.id.frameLayout, imgFragment)
                    fragment.commit()
                }
            }
            button2.setOnClickListener {
                if (!itemFragment.isVisible) {
                    fragment.addToBackStack(null)
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.frameLayout, itemFragment)
                    fragment.commit()
                }
            }
        }
    }

}