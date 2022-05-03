package dev.bong.mobileprogramming.week09

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.FragmentImageBinding

class DynamicExImageFragment : Fragment() {

    var binding: FragmentImageBinding? = null
    val myViewModel: MyViewModel by activityViewModels()
    val imgList = arrayListOf<Int>(R.drawable.img1, R.drawable.img2, R.drawable.img3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            imageView.setOnClickListener {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val textFragment = TextFragment()
                fragment.addToBackStack(null)
                fragment.replace(R.id.frameLayout, textFragment)
                fragment.commit()
            }
            radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                when (i) {
                    R.id.radioButton1 -> {
                        myViewModel.setLiveData(0)
                    }
                    R.id.radioButton2 -> {
                        myViewModel.setLiveData(1)
                    }
                    R.id.radioButton3 -> {
                        myViewModel.setLiveData(2)
                    }
                }
                imageView.setImageResource(imgList[myViewModel.selectedNum.value!!])
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}