package com.trip.notificationtest

import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(javaClass.simpleName, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(javaClass.simpleName, "Message data payload: ${remoteMessage.data}")

            val builder = Config.createNotificationBuilder(this)
            val notification = Config.createCustomNotification(this, builder, Intent())
            NotificationManagerCompat.from(this).notify(Config.NOTI_ID_DEFAULT, notification)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(javaClass.simpleName, "Message Notification Title: ${it.title}")
            Log.d(javaClass.simpleName, "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {

    }

}