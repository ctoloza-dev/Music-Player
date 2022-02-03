package com.music_player

import android.Manifest

class Globals {
    companion object {
        var permits = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
    }
}