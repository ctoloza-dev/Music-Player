package com.music_player.views

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.music_player.R
import com.music_player.databinding.ActivityPlayerBinding
import com.music_player.utils.Globals
import com.music_player.utils.Globals.Companion.ExtrasNames.*
import com.music_player.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by David on 26-01-2022.
 */
@AndroidEntryPoint
class PlayerActivity : BaseActivity() {
    private var binding: ActivityPlayerBinding? = null
    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        initOptions()
    }

    private fun initOptions() {
        val btnView = findViewById<BottomNavigationView>(R.id.opts)
        playerViewModel.optionsMenu.observe(this) { listMenu ->
            listMenu.forEachIndexed { index, menu ->
                btnView.menu.add(Menu.NONE, index, Menu.NONE, menu.btn).setIcon(menu.drawable)
            }
        }

        playerViewModel.getMenu()
        initializeLayout()
        binding!!.playPause.setOnClickListener { playerViewModel.onClick.onCLick(it) }
        binding!!.prevSong.setOnClickListener { playerViewModel.onClick.onCLick(it) }
        binding!!.nextSong.setOnClickListener { playerViewModel.onClick.onCLick(it) }
    }

    private fun initializeLayout() {
        val position = intent.getIntExtra(INDEX.description, 0)
        val clazz = intent.getSerializableExtra(CLAZZ.description)
        var filter = intent.getSerializableExtra(FILTER.description)
        if (filter == null) filter = Globals.Companion.FILTERS.NONE
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
        playerViewModel.loadSongList(clazz, position, filter)
    }


}