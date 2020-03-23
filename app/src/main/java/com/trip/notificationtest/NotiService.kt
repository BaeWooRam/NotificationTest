package com.trip.notificationtest

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews


class NotiService : Service() {
    companion object {
        const val CHANNEL_ID = "snwodeer_service_channel"
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun startForegroundService() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val remoteViews = RemoteViews(packageName, R.layout.notification)
        var builder: Notification.Builder
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= 26) {


            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "공지",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.description = "channel description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)

            val defaultSoundUri: Uri =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val att = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            notificationChannel.setSound(defaultSoundUri, att)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 100, 200);
            notificationManager.createNotificationChannel(notificationChannel)

            builder =
                Notification.Builder(applicationContext, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("title")
                    .setContentText("contents")
                    .setAutoCancel(true)
                    .setTicker("Ticker!")
                    .setContentIntent(pendingIntent)
        }else{
            builder =
                Notification.Builder(applicationContext)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("title")
                    .setContentText("contents")
                    .setPriority(Notification.PRIORITY_MAX)
                    .setVibrate( longArrayOf(100, 200, 100, 200))
                    .setAutoCancel(true)
                    .setTicker("Ticker!")
                    .setContentIntent(pendingIntent)

        }

        notificationManager.notify(1000, builder.build())
    }
}