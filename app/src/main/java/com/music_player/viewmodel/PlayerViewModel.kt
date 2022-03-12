package com.music_player.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.music_player.R
import com.music_player.interfaces.OnClick
import com.music_player.repository.models.MenuModel
import com.music_player.repository.models.SongsData
import com.music_player.utils.Globals.Companion.FILTERS
import com.music_player.utils.logs.Logger
import com.music_player.viewmodel.SongsViewModel.Companion.listSong
import com.music_player.viewmodel.adapters.MusicAdapter
import com.music_player.views.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by David on 02-03-2022.
 */
@HiltViewModel
class PlayerViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModelUtils(context) {
    var optionsMenu = MutableLiveData<ArrayList<MenuModel>>()
    var playPauseDrawable = MutableLiveData<Int>()
    val currentSong = MutableLiveData<SongsData>()

    private var position: Int = 0
    var songsList = ArrayList<SongsData>()


    @Inject
    lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false

    fun getMenu() {
        val list = arrayListOf<MenuModel>()
        list.add(MenuModel(getString(R.string.repeat), R.drawable.ico_repeat))
        list.add(MenuModel(getString(R.string.equalizer), R.drawable.ico_equalizer))
        list.add(MenuModel(getString(R.string.timer), R.drawable.ico_timer))
        list.add(MenuModel(getString(R.string.share), R.drawable.ico_share))
        optionsMenu.postValue(list)
    }

    fun loadSongList(clazz: Serializable?, pos: Int, filter: Serializable?) {
        position = pos
        songsList.addAll(listSong)
        mediaPlayer.reset()
        songsFilter(filter)
        when (clazz) {
            MusicAdapter::class.java, MainActivity::class.java ->
                createMediaPlayer()
        }
    }

    private fun songsFilter(filter: Serializable?) {
        when (filter) {
            FILTERS.NONE -> Logger.info("No Filter present")
            FILTERS.SHUFFLE -> songsList.shuffle()
            FILTERS.FAVOURITE -> {}
        }
    }

    private fun createMediaPlayer() {
        val currSong = songsList[position]
        currentSong.postValue(currSong)
        try {
            mediaPlayer.setDataSource(currSong.path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            playPause()
        } catch (e: Exception) {
            Logger.exception("Error creating a mediaPlayer", e)
            return
        }
    }

    val onClick = object : OnClick {
        override fun onCLick(v: View) {
            when (v.contentDescription) {
                getString(R.string.play_pause) -> playPause()
                getString(R.string.prev_song) -> prevNextSong(false)
                getString(R.string.next_song) -> prevNextSong(true)
            }
            Logger.error("Data: ${v.contentDescription}")
        }
    }

    private fun prevNextSong(increment: Boolean) {
        if (increment) {
            if (songsList.size - 1 == position)
                position = 0
            else ++position
        } else {
            if (0 == position)
                position = songsList.size - 1
            else --position
        }
        isPlaying = false
        createMediaPlayer()
    }

    private fun playPause() {
        if (isPlaying) {
            mediaPlayer.pause()
            playPauseDrawable.postValue(R.drawable.ico_play)
        } else {
            mediaPlayer.start()
            playPauseDrawable.postValue(R.drawable.ico_pause)
        }
        isPlaying = !isPlaying
    }
}