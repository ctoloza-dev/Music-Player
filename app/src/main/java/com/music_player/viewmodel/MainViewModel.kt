package com.music_player.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.music_player.R
import com.music_player.interfaces.OnClick
import com.music_player.repository.models.MenuModel
import com.music_player.utils.Globals.Companion.ExtrasNames
import com.music_player.utils.Globals.Companion.FILTERS
import com.music_player.utils.ResponseListener
import com.music_player.views.MainActivity
import com.music_player.views.PlayerActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by David on 02-03-2022.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModelUtils(context) {
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
                    utils.showDialogPermission(response!!)
                }
            }
        })
    }

    val onClick = object : OnClick {
        override fun onCLick(v: View) {
            if (SongsViewModel.listSong.isNotEmpty())
                if (v.contentDescription != null) {
                    when (v.contentDescription) {
                        getString(R.string.shuffle) -> context.openActivity(PlayerActivity::class.java) {
                            putInt(getDef(ExtrasNames.INDEX), 0)
                            putSerializable(getDef(ExtrasNames.CLAZZ), MainActivity::class.java)
                            putSerializable(getDef(ExtrasNames.FILTER), FILTERS.SHUFFLE)
                        }
                        getString(R.string.favourites) -> context.openActivity(PlayerActivity::class.java)
                        getString(R.string.playlist) -> context.openActivity(PlayerActivity::class.java)
                        else -> {}
                    }
                }
        }
    }
}