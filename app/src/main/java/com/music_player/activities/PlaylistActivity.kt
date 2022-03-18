package com.music_player.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.music_player.R
import com.music_player.views.BaseActivity

/**
 * Created by David on 28-01-2022.
 */

class PlaylistActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
    }
}