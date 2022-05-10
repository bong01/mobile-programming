package dev.bong.mobileprogramming.week10

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.bong.mobileprogramming.databinding.ActivityWebViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WebViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebViewBinding
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            button.setOnClickListener {
                scope.launch {
                    Log.i("CheckScope", Thread.currentThread().name)
                    progressBar.visibility = View.VISIBLE
                    var data = ""
                    // 1. withContext 를 이용하는 방법
                    withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                        Log.i("CheckScope", Thread.currentThread().name)
                        data = loadNetwork(URL(editText.text.toString()))
                    }
                    // 2. async-await 을 이용하는 방법
//                    CoroutineScope(Dispatchers.IO).async {
//                        Log.i("CheckScope", Thread.currentThread().name)
//                        data = loadNetwork(URL(editText.text.toString()))
//                    }.await()
                    Log.i("CheckScope", Thread.currentThread().name)
                    textView.text = data
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun loadNetwork(url: URL): String {
        var result = "";
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 4000
        connection.readTimeout = 4000
        connection.requestMethod = "GET"
        connection.connect()

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            result = streamToString(connection.inputStream)
        }

        return result
    }

    private fun streamToString(inputStream: InputStream) : String{
        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {
            Log.e("error", "읽기 싫패")
        }

        return result
    }

}