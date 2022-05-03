package dev.bong.mobileprogramming.week09

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.FragmentTextBinding

class TextFragment : Fragment() {

    var binding: FragmentTextBinding? = null
    val myViewModel : MyViewModel by activityViewModels()
    val data = arrayListOf<String>("ImageData1", "ImageData2", "ImageData3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val i = requireActivity().intent
        val imgNum = i.getIntExtra("imgNum", -1)

        if (imgNum != -1) {
            binding!!.textView.text = data[imgNum]
        } else {
            //인텐트로 못 받아왔을 경우
            myViewModel.selectedNum.observe(viewLifecycleOwner, Observer {
                binding!!.textView.text = data[it]
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}