package dev.bong.mobileprogramming.week15

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {

    lateinit var binding:ActivityServiceBinding
    var thread: Thread? = null
    var num = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            btnStartThread.setOnClickListener {
                if (thread == null) {
                    thread = object : Thread("MyThread") {

                        //스레드는 run이 다 수행이 되면 종료됨
                        override fun run() {

                            try {
                                for (i in 0..10) {
                                    Log.i("MyThread $num", "Count: $i")
                                    Thread.sleep(1000)
                                }
                            } catch (e: InterruptedException) {
                                Thread.currentThread().interrupt()
                            }

                            thread = null
                        }

                    }
                    thread!!.start()
                    num++
                }
            }

            btnStopThread.setOnClickListener {
                if (thread != null) {
                    thread!!.interrupt()
                }
                thread = null
            }

            btnStartService.setOnClickListener {
                val intent = Intent(this@ServiceActivity, MyService::class.java)
                startService(intent)
            }

            btnStopService.setOnClickListener {
                val intent = Intent(this@ServiceActivity, MyService::class.java)
                stopService(intent)
            }
        }
    }

}