package com.music_player.views

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.music_player.R
import com.music_player.adapters.OptionsAdapter
import com.music_player.databinding.ActivityPlayerBinding
import com.music_player.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by David on 26-01-2022.
 */
@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {
    private var binding: ActivityPlayerBinding? = null
    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        initOptions()
    }

    private fun initOptions() {
        val recycler = findViewById<View>(R.id.opts) as RecyclerView
        playerViewModel.optionsMenu.observe(this) { listMenu ->
            recycler.adapter = OptionsAdapter(listMenu, false, playerViewModel.onClick)
            recycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycler.setHasFixedSize(true)
            recycler.isNestedScrollingEnabled = false
        }
        playerViewModel.getMenu()
        initializeLayout()
        binding!!.playPause.setOnClickListener { playerViewModel.onClick.onCLick(it) }
        binding!!.prevSong.setOnClickListener { playerViewModel.onClick.onCLick(it) }
        binding!!.nextSong.setOnClickListener { playerViewModel.onClick.onCLick(it) }
    }

    private fun initializeLayout() {
        val position = intent.getIntExtra("index", 0)
        val clazz = intent.getSerializableExtra("class")
        playerViewModel.currentSong.observe(this) {
            Glide.with(this)
                .load(it.artUri)
                .apply(RequestOptions().placeholder(R.drawable.splash).centerCrop())
                .into(binding!!.songImage)
            binding!!.songName.text = it.title
        }
        playerViewModel.playPauseDrawable.observe(this) {
            binding!!.playPause.setIconResource(it)
        }
        playerViewModel.loadSongList(clazz, position)
    }


}