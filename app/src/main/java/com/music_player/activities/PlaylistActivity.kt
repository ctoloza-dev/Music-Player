package com.music_player.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.music_player.R

/**
* Created by David on 28-01-2022.
*/

class PlaylistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
    }
}