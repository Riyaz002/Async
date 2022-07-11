package com.riyaz.async.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.riyaz.async.R
import com.riyaz.async.other.Constants

class MusicService: Service() {
    private val myBinder = MyIBinder()
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        Log.d("Music service","onCreate: ${Thread.currentThread().name}")
        super.onCreate()
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.eery_her)
        mediaPlayer.setOnCompletionListener {
            Log.d("Music service", "music completed")
            val intent = Intent(Constants.MUSIC_COMPLETED_ACTION)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
        }
    }

    inner class MyIBinder: Binder(){
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d("Music service","onBind: ${Thread.currentThread().name}")
        return myBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("Music service","onUnbind: ${Thread.currentThread().name}")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("Music service","onDestroy: ${Thread.currentThread().name}")
        super.onDestroy()
        mediaPlayer.release()
    }

    fun isPlaying(): Boolean{
        return mediaPlayer.isPlaying
    }

    fun play(){
        mediaPlayer.start()
        Log.d("Music service", "play")
    }

    fun pause(){
        mediaPlayer.pause()
        Log.d("Music service", "pause")
    }


}