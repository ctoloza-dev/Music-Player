package com.music_player.viewmodel

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.music_player.R
import com.music_player.interfaces.OnClick
import com.music_player.repository.models.MenuModel
import com.music_player.repository.models.SongsData
import com.music_player.repository.models.getImgArt
import com.music_player.utils.Globals.Companion.FILTERS
import com.music_player.utils.Globals.Companion.NotificationItems.*
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
    private val _currentSong = MutableLiveData<SongsData>()
    val currentSong: LiveData<SongsData>
        get() = _currentSong

    private var position: Int = 0
    private var songsList = ArrayList<SongsData>()
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
        _currentSong.postValue(currSong)
        showNotification(currSong)
        try {
            mediaPlayer.reset()
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

    private fun showNotification(currSong: SongsData) {
        Logger.error(Gson().toJson(currSong))
        val imgArt = getImgArt(currSong.path!!)
        val image = when {
            imgArt != null -> BitmapFactory.decodeByteArray(imgArt, 0, imgArt.size)
            else -> BitmapFactory.decodeResource(context.resources, R.drawable.splash)
        }
        val notification = notification
            .setContentTitle(currSong.title)
            .setContentText(currSong.artists)
            .updateIcon(image)
            .addNotificationAction(R.drawable.ico_prev, getString(R.string.prev_song), PREVIOUS)
            .addNotificationAction(R.drawable.ico_play, getString(R.string.play_pause), PLAY)
            .addNotificationAction(R.drawable.ico_next, getString(R.string.next_song), NEXT)
            .addNotificationAction(R.drawable.ico_exit, getString(R.string.exit), EXIT)
            .build()
        startForeground(13, notification)
    }
}