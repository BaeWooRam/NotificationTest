package com.trip.notificationtest

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 * API별 Foreground Test Activity
 */
class ForegroundServiceActivity : AppCompatActivity(R.layout.activity_main), View.OnClickListener {
    var serviceIntent:Intent? = null
    var serviceIntent2:Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button.setOnClickListener(this)
        switch1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button -> {
                serviceIntent = Intent(applicationContext, ForegroundTestService::class.java).apply { action = "startForeground"}
                serviceIntent2 = Intent(applicationContext, ForegroundTestService2::class.java).apply { action = "startForeground"}

                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent)
                    startForegroundService(serviceIntent2)
                } else {
                    startService(serviceIntent)
                    startService(serviceIntent2)
                }

            }

            R.id.switch1 -> {
                Config.isSound = !Config.isSound
                Log.i("isSound","value = ${Config.isSound}")
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(serviceIntent)
    }
}
