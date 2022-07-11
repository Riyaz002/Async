package com.riyaz.async.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Message
import android.util.Log
import com.riyaz.async.other.DownloadThread

class DownloadService : Service() {
    private lateinit var thread: DownloadThread
    override fun onCreate() {
        Log.d("Service", "onCreate")
        thread = DownloadThread()
        thread.start()
        while (thread.downloadHandler == null) {

        }

        thread.downloadHandler!!.setService(this)
        thread.downloadHandler!!.setContext(applicationContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "onStartCommand")

        val message = Message.obtain()
        message.obj = intent?.getStringExtra("song").toString()
        message.arg1 = startId
        thread.downloadHandler?.sendMessage(message)
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d("Service", "Destroyed")
        super.onDestroy()
    }
}
