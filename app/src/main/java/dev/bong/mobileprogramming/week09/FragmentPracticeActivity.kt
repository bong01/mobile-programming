package dev.bong.mobileprogramming.week09

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.ActivityFragmentPracticeBinding

class FragmentPracticeActivity : AppCompatActivity() {

    lateinit var binding: ActivityFragmentPracticeBinding
    val textArr = arrayListOf<String>("이미지", "리스트")
    val iconArr = arrayListOf<Int>(R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFragmentPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        binding.viewPager.adapter = MyViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = textArr[position]
            tab.setIcon(iconArr[position])
        }.attach()
    }

}