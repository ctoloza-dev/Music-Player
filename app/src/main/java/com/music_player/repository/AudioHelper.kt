package com.music_player.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.music_player.models.SongsData
import java.io.File
import javax.inject.Inject

/**
 * Created by David on 02-03-2022.
 */
class AudioHelper @Inject constructor() {
    @Inject
    lateinit var context: Context

    fun getAllAudio(): ArrayList<SongsData> {
        val list = arrayListOf<SongsData>()
        val selection =
            "${MediaStore.Audio.Media.IS_MUSIC} !=0 " +
                    "AND  ${MediaStore.Audio.Media.MIME_TYPE}='audio/mpeg'" +
                    "AND ${MediaStore.Audio.Media.DURATION}  > 60000 " +
                    "AND ${MediaStore.Audio.Media.RELATIVE_PATH}  like '%Music%' "
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.RELATIVE_PATH,
            MediaStore.Audio.Media.DISPLAY_NAME
        )
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            MediaStore.Audio.Media.DISPLAY_NAME + " ASC",
            null
        )
        val music = HashMap<Int, Any>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                projection.forEachIndexed { index, s ->
                    val idx = cursor.getColumnIndex(s)
                    if (s != MediaStore.Audio.Media.DATE_ADDED) {
                        music[index] = when (cursor.getType(idx)) {
                            Cursor.FIELD_TYPE_STRING -> cursor.getString(idx)
                            Cursor.FIELD_TYPE_INTEGER -> cursor.getLong(idx)
                            else -> ""
                        }
                    }
                }
//                Logger.info("${music[8]}  - ${music[1]}")
                val uri = Uri.parse("content://media/external/audio/albumart")
                val artUri = Uri.withAppendedPath(uri, music[7].toString()).toString()

                val song =
                    SongsData(
                        music[0].toString(),
                        music[1].toString(),
                        music[2].toString(),
                        music[3].toString(),
                        music[4]!!.toString().toLong(),
                        music[6].toString(),
                        artUri,
                        music[music.size - 1].toString()
                    )
                val file = File(song.path!!)
                if (file.exists())
                    list.add(song)

//                    Logger.error("$song")
            } while (cursor.moveToNext())
            cursor.close()
        }
        return list
    }
}