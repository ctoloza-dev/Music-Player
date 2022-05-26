package com.music_player.modules

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.music_player.utils.Globals
import com.music_player.utils.Globals.Companion.NotificationItems

/**
 * Created by David on 23/03/2022
 */
class NotificationReceiver : HiltBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val msg = when (intent.action) {
            getDef(NotificationItems.PREVIOUS) -> getDef(NotificationItems.PREVIOUS)
            getDef(NotificationItems.PLAY) -> getDef(NotificationItems.PLAY)
            getDef(NotificationItems.NEXT) -> getDef(NotificationItems.NEXT)
            getDef(NotificationItems.EXIT) -> getDef(NotificationItems.EXIT)
            else -> "Any action"
        }
        Toast.makeText(context, "$msg clicked", Toast.LENGTH_SHORT).show()
    }

    private fun getDef(name: Globals.enumBasic) = name.getDesc()
}