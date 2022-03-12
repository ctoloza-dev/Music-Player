package com.music_player.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModel
import com.music_player.R
import com.music_player.activities.FavouriteActivity
import com.music_player.activities.PlaylistActivity
import com.music_player.utils.Globals.Companion.ExtrasNames
import com.music_player.utils.PermissionStatus
import com.music_player.utils.Utilities
import com.music_player.views.PlayerActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by David on 02-03-2022.
 */
@HiltViewModel
open class ViewModelUtils @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {
    @Inject
    lateinit var permissionStatus: PermissionStatus


//    val context by lazy { application }

    @Inject
    lateinit var utils: Utilities


    fun getString(id: Int) = context.getString(id)

    fun getDrawable(id: Int) = AppCompatResources.getDrawable(context, id)


    fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
        val intent = Intent(context, it)
        intent.putExtras(Bundle().apply(extras))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun getDef(name: ExtrasNames) = name.description

}