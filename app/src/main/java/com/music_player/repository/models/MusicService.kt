package com.music_player.repository.models

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
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

    inner class MusicBinder : Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }
}