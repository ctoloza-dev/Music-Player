package com.music_player.modules

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.music_player.utils.logs.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by David on 13/03/2022
 */
@AndroidEntryPoint
class MusicService : Service() {
    @Inject
    lateinit var mediaPlayer: MediaPlayer

    private var musicBinder = MusicBinder()

    override fun onBind(intent: Intent?): IBinder {
        return musicBinder
    }

    fun startFore(id: Int, notification: Notification?) {
        startForeground(id, notification)
    }

    inner class MusicBinder : Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }
}