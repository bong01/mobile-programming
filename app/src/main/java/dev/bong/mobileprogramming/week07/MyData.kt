package dev.bong.mobileprogramming.week07

import android.graphics.drawable.Drawable

data class MyData(
    var applabel: String,
    var appclass: String,
    var apppackname: String,
    var appicon: Drawable
) : java.io.Serializable