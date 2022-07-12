package com.riyaz.async.services

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.riyaz.async.R
import com.riyaz.async.other.Constants

class MusicThread(val context: Context): Thread() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun run() {

    }

}