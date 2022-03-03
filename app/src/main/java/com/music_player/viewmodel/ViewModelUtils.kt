package com.music_player.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModel
import com.music_player.R
import com.music_player.activities.FavouriteActivity
import com.music_player.activities.PlaylistActivity
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

    fun startActivity(clazz: Class<*>?) =
        when {
            (clazz != null) -> context.startActivity(Intent(context, clazz))
            else -> {}
        }

    fun getClassByDesc(contentDescription: CharSequence): Class<*>? {
        return when (contentDescription) {
            getString(R.string.shuffle) -> PlayerActivity::class.java
            getString(R.string.favourites) -> FavouriteActivity::class.java
            getString(R.string.playlist) -> PlaylistActivity::class.java
            else -> null
        }
    }

}