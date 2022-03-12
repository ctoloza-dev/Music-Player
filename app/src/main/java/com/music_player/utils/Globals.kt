package com.music_player.utils

import android.Manifest

/**
 * Created by David on 02-03-2022.
 */
open class Globals {
    companion object {

        var permits = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
        const val printStack = true

        const val PAD_CENTER = 0
        const val PAD_LEFT = 1
        const val PAD_RIGHT = 2

        enum class ExtrasNames(val description: String) {
            CLAZZ("clazz"),
            INDEX("index"),
            FILTER("filter")
        }

        enum class FILTERS {
            NONE,
            SHUFFLE,
            FAVOURITE,
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