package com.stocky.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.stocky.R
import com.stocky.activities.SplashActivity

class FirebasePushNotification : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "New token: $token")
    }

    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0,notificationBuilder.build())
    }

    companion object {
        private const val TAG = "FirebasePushNotification"
        private const val CHANNEL_ID = "notification_channel"
        private const val CHANNEL_NAME = "com.stocky"
    }
}