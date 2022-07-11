package com.riyaz.async.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.riyaz.async.MainActivity

class MyLocalReceiver(val function: () -> Unit) : BroadcastReceiver() {
    constructor():this({}){

    }
    override fun onReceive(context: Context?, intent: Intent?) {
        function.invoke()
    }
}