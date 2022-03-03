package com.music_player.models

import java.util.concurrent.TimeUnit

/**
 * Created by David on 07-02-2022.
 */
data class SongsData(
    val id: String?,
    val title: String?,
    val album: String?,
    val artists: String?,
    val duration: Long? = 0,
    val path: String?,
    val artUri: String?,
    val display: String?
)

fun formatDuration(duration: Long): String {
    val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val seconds = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
            - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
    return String.format("%02d:%02d", minutes, seconds)
}