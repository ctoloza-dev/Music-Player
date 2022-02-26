package com.music_player.viewmodel

import android.app.Application
import com.music_player.models.SongsData
import com.music_player.repository.AudioHelper
import java.util.*

class SongsViewModel(application: Application) : ViewModelUtils(application) {
    private var audioHelper = AudioHelper(context)

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
