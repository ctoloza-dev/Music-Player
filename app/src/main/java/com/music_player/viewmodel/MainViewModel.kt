package com.music_player.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.music_player.R
import com.music_player.activities.FavouriteActivity
import com.music_player.activities.PlayerActivity
import com.music_player.activities.PlaylistActivity
import com.music_player.interfaces.OnClick
import com.music_player.models.MenuModel
import com.music_player.utils.ResponseListener
import com.music_player.utils.UtilitiesImpl

class MainViewModel(application: Application) : ViewModelUtils(application) {
    var optionsMenu = MutableLiveData<ArrayList<MenuModel>>()
    var isPermissionGive = MutableLiveData<Boolean>()

    fun getMenu() {
        val list = arrayListOf<MenuModel>()
        list.add(MenuModel(getString(R.string.shuffle), R.drawable.ico_shuffle))
        list.add(MenuModel(getString(R.string.favourites), R.drawable.ico_favorite))
        list.add(MenuModel(getString(R.string.playlist), R.drawable.ico_playlist))
        optionsMenu.postValue(list)
    }

    fun checkPermissions() {
        permissionStatus.checkPermissions(object : ResponseListener {
            override fun onResponse(response: String?) {
                isPermissionGive.postValue(response.equals(getString(R.string.perm_granted)))
            }
        })
    }

    fun requestPerms() {
        permissionStatus.checkPermissions(object : ResponseListener {
            override fun onResponse(response: String?) {
                if (response != getString(R.string.perm_granted)) {
                    isPermissionGive.postValue(false)
                    UtilitiesImpl(context).showDialogPermission(response!!)
                }
            }
        })
    }

    val onClick = object : OnClick {
        override fun onCLick(v: View) {
            var clazz: Class<*>? = null
            if (v.contentDescription != null) {
                clazz = when {
                    v.contentDescription.equals(getString(R.string.shuffle)) -> {
                        PlayerActivity::class.java
                    }
                    v.contentDescription.equals(getString(R.string.favourites)) -> {
                        FavouriteActivity::class.java
                    }
                    v.contentDescription.equals(getString(R.string.playlist)) -> {
                        PlaylistActivity::class.java
                    }
                    else -> {
                        null
                    }
                }
            }
            if (clazz != null) {
                startActivity(clazz)
            }
        }
    }
}