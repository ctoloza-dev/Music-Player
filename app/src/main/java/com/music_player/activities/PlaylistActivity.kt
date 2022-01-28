package com.music_player.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.music_player.R

class PlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
    }
}