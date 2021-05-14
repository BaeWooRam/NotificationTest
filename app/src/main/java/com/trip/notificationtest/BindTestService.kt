
package com.trip.notificationtest

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.trip.notificationtest.Config.isSound
import com.trip.notificationtest.Config.notificationID
import java.util.*


class BindTestService : Service() {
    inner class LocalBinder :Binder(){
        fun getService():BindTestService = this@BindTestService
    }

    private val binder = LocalBinder()
    // Random number generator
    private val mGenerator = Random()

    /** method for clients  */
    val randomNumber: Int
        get() = mGenerator.nextInt(100)

    override fun onBind(intent: Intent?): IBinder? {
       return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val TAG = "BindTestService"
    }
}
