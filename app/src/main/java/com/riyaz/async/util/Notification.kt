package com.riyaz.async.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.riyaz.async.MainActivity
import com.riyaz.async.R

const val PENDING_INTENT_CODE = 21
const val NOTIFICATION_ID = 1

fun NotificationManager.sendNotification(context: Context, channelId: String,title: String): Notification{

    val notificationIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, PENDING_INTENT_CODE, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText("playing sara's love song..")
        .setSmallIcon(R.drawable.ic_baseline_music_note_24)
        .setContentIntent(pendingIntent)
        .setAutoCancel(false)

    return notificationBuilder.build()
}

fun createNotificationChannel(context: Context, channelName: String, channelId: String,channelDescription: String) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
           context.getSystemService<NotificationManager>() as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}