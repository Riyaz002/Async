package com.riyaz.async.services

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.riyaz.async.other.Constants
import com.riyaz.async.other.DownloadHandler

// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
const val ACTION_DOWNLOAD_BOOK = "com.riyaz.async.services.action.DownloadBook"
const val ACTION_DOWNLOAD_SONG = "com.riyaz.async.services.action.DownloadSong"

// TODO: Rename parameters
private const val BOOK = "book"
private const val SONG = "song"
/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.

 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.

 */
class MyDownloadIntentService : IntentService("MyDownloadIntentService") {
    override fun onCreate() {
        super.onCreate()
        setIntentRedelivery(true)
    }
    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_DOWNLOAD_BOOK -> {
                val bookName = intent.getStringExtra(BOOK).toString()
                downloadBook(bookName)
            }
            ACTION_DOWNLOAD_SONG-> {
                val song = intent.getStringExtra(SONG).toString()
                downloadSong(song)
            }
        }
    }

    /**
     * Handle download book_action in the provided background thread with the provided
     * parameters.
     */
    private fun downloadBook(book: String) {
        Log.d(Constants.DOWNLOAD_TAG, "downloading book: $book...")
        Thread.sleep(2000L)
        Log.d(Constants.DOWNLOAD_TAG, "The book: $book is downloaded")

        //broadcast
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(Intent(DownloadHandler.DOWNLOAD_BROADCAST).apply {
            putExtra("info", "$book downloaded")
        })
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun downloadSong(song: String?) {
        Log.d(Constants.DOWNLOAD_TAG, "downloading book: $song...")
        Thread.sleep(2000L)
        Log.d(Constants.DOWNLOAD_TAG, "The book: $song is downloaded")

        //broadcast
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(Intent(DownloadHandler.DOWNLOAD_BROADCAST).apply {
            putExtra("info", "$song downloaded")
        })
    }

    companion object {
        /**
         * Starts this service to perform download_book_action with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun downloadBook(context: Context, book: String) {
            val intent = Intent(context, MyDownloadIntentService::class.java).apply {
                action = ACTION_DOWNLOAD_BOOK
                putExtra(BOOK, book)
            }
            context.startService(intent)
        }
    }
}