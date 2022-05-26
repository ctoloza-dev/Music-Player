package com.music_player.viewmodel

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import com.music_player.modules.MusicService
import com.music_player.modules.NotificationReceiver
import com.music_player.repository.models.SongsData
import com.music_player.utils.Globals
import com.music_player.utils.PermissionStatus
import com.music_player.utils.Utilities
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by David on 02-03-2022.
 */
@HiltViewModel
open class ViewModelUtils @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {
    @Inject
    lateinit var permissionStatus: PermissionStatus

    @Inject
    lateinit var utils: Utilities

    @Inject
    lateinit var notification: NotificationCompat.Builder

    companion object {
        lateinit var mediaPlayer: MediaPlayer
        private var musicService: MusicService? = null
    }

    fun getString(id: Int) = context.getString(id)

    fun getDrawable(id: Int) = AppCompatResources.getDrawable(context, id)


    fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
        val intent = Intent(context, it)
        intent.putExtras(Bundle().apply(extras))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun startForeground(id: Int, notification: Notification?) {
        musicService!!.startFore(id, notification)
    }

    fun getDef(name: Globals.enumBasic) = name.getDesc()

    fun setMService(mService: MusicService?) {
        musicService = mService
        if (mService != null) {
            mediaPlayer = mService.mediaPlayer
        }
    }

    private fun getNotificationIntent(notificationItem: Globals.enumBasic): PendingIntent? {
        val intent =
            Intent(context, NotificationReceiver::class.java).setAction(getDef(notificationItem))

        var flag = PendingIntent.FLAG_ONE_SHOT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            flag = PendingIntent.FLAG_MUTABLE

        return PendingIntent.getBroadcast(context, 0, intent, flag)
    }

    fun NotificationCompat.Builder.addNotificationAction(
        icon: Int, title: CharSequence?, notificationItem: Globals.enumBasic
    ): NotificationCompat.Builder {
        return addAction(
            icon, title, getNotificationIntent(notificationItem)
        )
    }

    fun NotificationCompat.Builder.updateIcon(
        newIcon: Bitmap?
    ): NotificationCompat.Builder {
        this.setLargeIcon(newIcon)
        return this
    }
}