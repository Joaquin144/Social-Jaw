package com.devcommop.myapplication.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.devcommop.myapplication.MainActivity
import com.devcommop.myapplication.R
import com.devcommop.myapplication.utils.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import okhttp3.internal.notify
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        var sharedPreferences: SharedPreferences? = null
        var token: String?
            get() {
                return sharedPreferences?.getString("token", "")//retrieve token from local
            }
            set(value) {
                sharedPreferences?.edit()?.putString("token", value)?.apply()
            }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent = Intent(this, MainActivity::class.java)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId =
            Random.nextInt()   //we want all notifs to have different ids because any new notif with preexisting id would replace the older notifs which have id equal to this notif

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) //hover on it to see doc
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )
        val notification = NotificationCompat.Builder(this, Constants.DEFAULT_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.notification_icon_image)
            .setAutoCancel(true)//one click causes notif to be closed
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(Constants.DEFAULT_NOTIFICATION_CHANNEL_ID, Constants.DEFAULT_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "This is the default notification channel for SocialJaw app"
            enableLights(true)
            lightColor = Color.GREEN
        }
    }
}