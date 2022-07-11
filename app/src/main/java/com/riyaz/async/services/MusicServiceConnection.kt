package com.riyaz.async.services

import android.app.Activity
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.riyaz.async.MainActivity

class MusicServiceConnection(val mainActivity: Activity): ServiceConnection {
    override fun onServiceConnected(compName: ComponentName?, iBinder: IBinder?) {
//        val mainActivity = (mainActivity as MainActivity)
//        mainActivity.createToast("service is bounded")
//        val service = (iBinder as MusicService.MyIBinder).getService()
//        if(service == null){
//            Log.d("serviceConn","service is null")
//        } else{
//            Log.d("serviceConn","service is not null: ${Thread.currentThread().name}")
//        }
//        mainActivity.musicService = (iBinder as MusicService.MyIBinder).getService()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        (mainActivity as MainActivity).createToast("service is unbounded")
    }
}