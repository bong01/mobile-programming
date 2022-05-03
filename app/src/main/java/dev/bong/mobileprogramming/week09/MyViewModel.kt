package dev.bong.mobileprogramming.week09

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    val selectedNum = MutableLiveData<Int>(0)

    fun setLiveData(num: Int) {
        selectedNum.value = num
    }

}