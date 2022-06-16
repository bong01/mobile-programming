package dev.bong.mobileprogramming.week15

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    var thread: Thread? = null
    var num = 0

    override fun onCreate() {
        super.onCreate()
        Log.i("MyService", "onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("MyService", "onStartCommand()")

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

        return START_STICKY
    }

    override fun onDestroy() {
        Log.i("MyService", "onDestroy()")
        super.onDestroy()

        if (thread != null) {
            thread!!.interrupt()
        }
        thread = null
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}