package com.music_player.models

data class SongsData(
    val id: String?,
    val title: String?,
    val album: String?,
    val artists: String?,
    val duration: Long? = 0,
    val path: String?, val display: String?
)