package com.music_player.views

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.music_player.modules.MusicService
import com.music_player.viewmodel.ViewModelUtils

open class BaseActivity : AppCompatActivity(), ServiceConnection {

    private val viewModel: ViewModelUtils by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        startService(intent)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MusicBinder
        viewModel.setMService(binder.currentService())
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        viewModel.setMService(null)
    }
}