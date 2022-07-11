package com.riyaz.async.other

import android.os.Looper

class DownloadThread: Thread() {
    var downloadHandler: DownloadHandler? = null
    override fun run() {
        Looper.prepare()
        downloadHandler = DownloadHandler()
        Looper.loop()
    }
}