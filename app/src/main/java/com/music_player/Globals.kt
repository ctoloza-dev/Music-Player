package com.music_player

import android.Manifest

/**
* Created by David on 03-02-2022.
*/
class Globals {
    companion object {
        var permits = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
    }
}