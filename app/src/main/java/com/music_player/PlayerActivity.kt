package com.music_player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.music_player.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    private var binding: ActivityPlayerBinding? = null
    private var sName = "Unknown Song"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        binding!!.songName = sName
    }
}