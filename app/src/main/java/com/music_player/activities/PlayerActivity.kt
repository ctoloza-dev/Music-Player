package com.music_player.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.music_player.R
import com.music_player.adapters.MusicAdapter
import com.music_player.adapters.OptionsAdapter
import com.music_player.databinding.ActivityPlayerBinding
import com.music_player.interfaces.OnClick
import com.music_player.models.MenuModel
import com.music_player.models.SongsData
import com.music_player.utils.logs.Logger

class PlayerActivity : AppCompatActivity() {
    private var binding: ActivityPlayerBinding? = null

    companion object {
        var songPosition: Int = 0
        lateinit var musicList: ArrayList<SongsData>
        var mediaPlayer: MediaPlayer? = null
        lateinit var currentSong: SongsData
    }

    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        initOptions()
    }

    private fun initOptions() {
        val list = arrayListOf<MenuModel>()
        list.add(MenuModel(getString(R.string.repeat), R.drawable.ico_repeat))
        list.add(MenuModel(getString(R.string.equalizer), R.drawable.ico_equalizer))
        list.add(MenuModel(getString(R.string.timer), R.drawable.ico_timer))
        list.add(MenuModel(getString(R.string.share), R.drawable.ico_share))

        val recycler = findViewById<View>(R.id.opts) as RecyclerView
        recycler.adapter = OptionsAdapter(list, true, onClick)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.setHasFixedSize(true)
        recycler.isNestedScrollingEnabled = false
        initializeLayout()
        binding!!.playPause.setOnClickListener { playPause() }
    }

    private fun initializeLayout() {
        songPosition = intent.getIntExtra("index", 0)
        when (intent.getSerializableExtra("class")) {
            MusicAdapter::class.java -> {
                musicList = ArrayList()
                musicList.addAll(MainActivity.listSong)
                initLayout()
                createMediaPlayer()
            }
        }
    }

    private fun createMediaPlayer() {
        try {
            if (mediaPlayer == null) mediaPlayer = MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(currentSong.path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            playPause()
        } catch (e: Exception) {
            Logger.exception("Error creating a mediaPlayer", e)
            return
        }
    }

    private fun playPause() {
        val drawable: Int = if (!isPlaying) R.drawable.ico_pause else R.drawable.ico_play
        when (isPlaying) {
            true -> mediaPlayer!!.pause()
            else -> mediaPlayer!!.start()
        }
        isPlaying = !isPlaying
        binding!!.playPause.setIconResource(drawable)
    }

    private fun initLayout() {
        currentSong = musicList[songPosition]
        Glide.with(this)
            .load(currentSong.artUri)
            .apply(RequestOptions().placeholder(R.drawable.splash).centerCrop())
            .into(binding!!.songImage)
        binding!!.songName.text = currentSong.title
    }

    private val onClick = object : OnClick {
        override fun onCLick(v: View) {
            Logger.error("Data: ${v.contentDescription}")
        }
    }
}