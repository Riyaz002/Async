package com.riyaz.async

import android.app.NotificationManager
import android.content.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.riyaz.async.other.Constants
import com.riyaz.async.receiver.MyLocalReceiver
import com.riyaz.async.services.MusicService
import com.riyaz.async.util.NOTIFICATION_ID
import com.riyaz.async.util.sendNotification
import java.lang.IllegalStateException
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    var isServiceConnected = false
    var musicServiceConnection = this.MyServiceConnection()
    var musicService: MusicService? = null
    private var musicCompletionBroadcastReceiver = MyLocalReceiver(){
        if(isServiceConnected){
            musicService?.let {
                if(!it.isPlaying()){
                    button.text = "Play"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Main", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val books: ArrayList<String> = arrayListOf("book1", "book2", "book3", "book4")
        button = this.findViewById(R.id.button)
        textView = this.findViewById(R.id.textView)
        progressBar = this.findViewById(R.id.progressBar)
        this.button.setOnClickListener {
            if(isServiceConnected){
               if(musicService!!.isPlaying()){
                   musicService!!.pause()
                   button.text = "Resume"
               } else{
                   startMusicService()
                   musicService!!.play()
                   button.text = "Pause"
               }
           }
        }
    }

    private fun startMusicService() {
        val intent = Intent(applicationContext, MusicService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationContext.startForegroundService(intent)
        } else{
            startService(intent)
        }
    }

    private fun startBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(musicCompletionBroadcastReceiver, IntentFilter(Constants.MUSIC_COMPLETED_ACTION))
    }


    fun displayText(text: String){
        (textView.text.toString()+ "\n"+ text).also { textView.text = it }
    }

    override fun onStart() {
        super.onStart()
        createMusicService()
        startBroadcastReceiver()
        Log.d("Main", "onStart")
    }

    override fun onResume() {
        Log.d("Main", "onResume")
        super.onResume()
    }

    override fun onRestart() {
        Log.d("Main", "onRestart")
        super.onRestart()
    }

    private fun createMusicService(){
        val intent = Intent(this@MainActivity, MusicService::class.java)
        bindService(intent, musicServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onPause() {
        Log.d("Main", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("Main", "onStop")
        super.onStop()
        destroyMusicService()
    }

    fun createToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    inner class MyServiceConnection(): ServiceConnection{
        override fun onServiceConnected(compName: ComponentName?, iBinder: IBinder?) {
            Log.d("Music connection", "connected")
            isServiceConnected = true
            musicService = (iBinder as MusicService.MyIBinder).getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d("Music connection", "disconnected")
            isServiceConnected = false
        }
    }

    override fun onDestroy() {
        Log.d("Main", "onDestroy")
        destroyMusicService()
        stopService(Intent(this, MusicService::class.java))
        LocalBroadcastManager.getInstance(this).unregisterReceiver(musicCompletionBroadcastReceiver)
        super.onDestroy()
    }

    private fun destroyMusicService() {
        if(isServiceConnected){
            unbindService(musicServiceConnection)
            isServiceConnected = false
        }
    }
}




