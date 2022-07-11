package com.riyaz.async.other

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.ResultReceiver
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.riyaz.async.MainActivity
import com.riyaz.async.services.DownloadService

class DownloadHandler: Handler() {
    private lateinit var downloadService: DownloadService
    private lateinit var context: Context
    override fun handleMessage(msg: Message) {
        val book = msg.obj

        Log.d(Constants.DOWNLOAD_TAG, "downloading book: $book...")
        Thread.sleep(2000L)
        Log.d(Constants.DOWNLOAD_TAG, "The book: $book is downloaded")
        val intent = Intent(DOWNLOAD_BROADCAST)
        intent.putExtra("info", "$book downloaded")
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    fun setService(downloadService: DownloadService){
        this.downloadService = downloadService
    }

    fun setContext(applicationContext: Context?) {
        applicationContext?.let {
            context = it
        }
    }

    companion object {
        const val DOWNLOAD_BROADCAST = "Download Broadcast"
    }
}