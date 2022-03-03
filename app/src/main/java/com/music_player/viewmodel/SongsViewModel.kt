package com.music_player.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.music_player.models.SongsData
import com.music_player.repository.AudioHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

/**
* Created by David on 02-03-2022.
*/
@HiltViewModel
class SongsViewModel @Inject constructor(
    @ApplicationContext  context: Context
) : ViewModelUtils(context) {
    @Inject
    lateinit var audioHelper: AudioHelper

    companion object {
        var listSong = ArrayList<SongsData>()
    }

    fun getSongs(): ArrayList<SongsData> {
        listSong = audioHelper.getAllAudio()
        Collections.sort(listSong, CustomComparator())
        return listSong

    }

    class CustomComparator : Comparator<SongsData> {
        override fun compare(song1: SongsData, song2: SongsData): Int {
            val display1 = song1.display
            val display2 = song2.display
            return display1!!.substring(display1.indexOf("-") + 1)
                .compareTo(display2!!.substring(display2.indexOf("-") + 1))
        }

    }
}
