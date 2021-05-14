package com.trip.notificationtest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 * API별 Foreground Test Activity
 */
class BindServiceActivity : AppCompatActivity(R.layout.activity_main), View.OnClickListener {
    var serviceIntent:Intent? = null
    var serviceIntent2:Intent? = null

    private var mBindService:BindTestService? = null
    private var mBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as BindTestService.LocalBinder
            mBindService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button.setOnClickListener(this)
        switch1.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        Intent(this, BindTestService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button -> {
                if(mBound && mBindService != null){
                    mBindService!!.randomNumber?.let {
                        Toast.makeText(this, "number: $it", Toast.LENGTH_SHORT).show()
                    }
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
