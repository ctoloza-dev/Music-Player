package com.music_player.viewmodel

import android.app.Application
import android.content.Intent
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.AndroidViewModel
import com.music_player.utils.PermissionStatus

open class ViewModelUtils(application: Application) : AndroidViewModel(application) {

    val context by lazy { getApplication<Application>() }
    val permissionStatus = PermissionStatus(context)

    fun getString(id: Int) = context.getString(id)


    fun getDrawable(id: Int) = AppCompatResources.getDrawable(context, id)

    fun startActivity(clazz: Class<*>) = context.startActivity(Intent(context, clazz))

}