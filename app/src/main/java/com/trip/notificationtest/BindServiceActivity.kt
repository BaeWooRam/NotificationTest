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
 * BindService
 * 서비스가 시작되고 Bind를 허용했으면 stopSelf 또는 stopService를 무조건 해줘야한다.
 * 서비스가 실행되고 클라이언트에서 모두 바인드를 해제해도 서비스를 소멸시키지 않기 때문인다.
 *
 * 참고 : https://developer.android.com/guide/components/bound-services?hl=ko
 */
class BindServiceActivity : AppCompatActivity(R.layout.activity_main), View.OnClickListener {
    private val TAG = javaClass.simpleName
    private var mBindService:BindTestService? = null
    private var mBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.d(TAG, "onService Connected Start!")

            val binder = service as BindTestService.LocalBinder
            mBindService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d(TAG, "onService Disconnected Start!")
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
        if (mBound) {
            Log.d(TAG, "onStop unbindService connection!")
            unbindService(connection)
            mBound = false
        }
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
}
