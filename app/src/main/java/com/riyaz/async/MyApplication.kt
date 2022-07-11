package com.riyaz.async

import android.app.Application
import com.riyaz.async.util.createNotificationChannel

class MyApplication: Application() {
    override fun onCreate() {
        createNotificationChannel(this, "Song", "song", "playing song" )
        super.onCreate()
    }
}