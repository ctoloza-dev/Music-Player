package com.music_player.utils

import android.Manifest

/**
 * Created by David on 02-03-2022.
 */
open class Globals {
    interface enumBasic {
        fun getDesc(): String
    }

    companion object {

        var permits = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.FOREGROUND_SERVICE
        )
        const val printStack = true

        const val PAD_CENTER = 0
        const val PAD_LEFT = 1
        const val PAD_RIGHT = 2

        enum class ExtrasNames(val description: String) : enumBasic {
            CLAZZ("clazz"),
            INDEX("index"),
            FILTER("filter")
            ;

            override fun getDesc(): String = description

        }

        enum class NotificationItems(val description: String) : enumBasic {
            CHANNEL_ID("channel_id"),
            PLAY("play"),
            NEXT("next"),
            PREVIOUS("prev"),
            EXIT("exit");

            override fun getDesc(): String = description
        }

        enum class FILTERS : enumBasic {
            NONE,
            SHUFFLE,
            FAVOURITE;

            override fun getDesc(): String = name
        }

        fun padText(s: String?, size: Int, type: Int, c: Char): String {
            val result: String = when (type) {
                PAD_CENTER -> {
                    val sb = StringBuilder()
                    var i = 0
                    while (i < (size - s!!.length) / 2) {
                        sb.append(c)
                        i++
                    }
                    sb.append(s)
                    while (sb.length < size) {
                        sb.append(c)
                    }
                    sb.toString()
                }
                PAD_LEFT -> String.format("%" + size.toString() + "s", s)
                    .replace(' ', c)
                PAD_RIGHT -> String.format(
                    "%" + (-size).toString() + "s", s
                ).replace(' ', c)
                else -> s!!
            }
            return result
        }
    }
}