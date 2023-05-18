package com.appsnipp.eadvokate.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.appsnipp.eadvokate.activity.ForVideoAudioConsultationActivity
import java.util.*


class MyService : Service() {
    private val mHandler: Handler = Handler() // run on another Thread to avoid crash
    private var mTimer: Timer? = null // timer handling
    private var context: Context? = null // timer handling


    override fun onBind(intent: Intent?): IBinder {
        throw UnsupportedOperationException("unsupported Operation")
    }

    override fun onCreate() {
        // cancel if service is  already existed
        context=this
        if (mTimer != null) mTimer!!.cancel() else mTimer = Timer() // recreate new timer
        mTimer!!.scheduleAtFixedRate(TimeDisplayTimerTask(), 0, INTERVAL) // schedule task
    }

    override fun onDestroy() {
        Toast.makeText(this, "In Destroy", Toast.LENGTH_SHORT).show() //display toast when method called
        mTimer!!.cancel() //cancel the timer
    }

    //inner class of TimeDisplayTimerTask
    private inner class TimeDisplayTimerTask : TimerTask() {

        override fun run() {
            // run on another thread
            mHandler.post(Runnable { // display toast at every 10 second
             Toast.makeText(context, "Notify", Toast.LENGTH_SHORT).show()
            })
        }
    }

    companion object {
        const val INTERVAL: Long = 1000//variable to execute services every 1 second
    }
}