package dev.bong.mobileprogramming.week10

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import dev.bong.mobileprogramming.databinding.ActivityVolleyBinding

class VolleyActivity : AppCompatActivity() {

    lateinit var binding: ActivityVolleyBinding
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolleyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        requestQueue = Volley.newRequestQueue(this)
        binding.apply {
            button.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val stringRequest = StringRequest(
                    Request.Method.GET,
                    editText.text.toString(),
                    Response.Listener<String> {
                        textView.text = String(it.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
                        progressBar.visibility = View.GONE
                    },
                    Response.ErrorListener {
                        textView.text = it.message
                    }
                )
                requestQueue.add(stringRequest)
            }
        }
    }
}