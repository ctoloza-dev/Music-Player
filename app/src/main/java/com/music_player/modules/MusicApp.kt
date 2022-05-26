package com.music_player.modules

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.music_player.R
import com.music_player.utils.Globals.Companion.NotificationItems
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by David on 02-03-2022.
 */
@HiltAndroidApp
class MusicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    NotificationItems.CHANNEL_ID.description,
                    getString(R.string.now_playing),
                    NotificationManager.IMPORTANCE_HIGH
                )
            notificationChannel.description = getString(R.string.notification_desc)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}