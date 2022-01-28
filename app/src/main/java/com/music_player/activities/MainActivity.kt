package com.music_player.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music_player.R
import com.music_player.adapters.OptionsAdapter
import com.music_player.databinding.ActivityMainBinding
import com.music_player.interfaces.OnClick
import com.music_player.models.MenuModel
import com.music_player.utils.PermissionStatus


class MainActivity : AppCompatActivity() {
    private var totalSongs = 0
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestRuntimePerms()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initOptions()
        binding!!.total = totalSongs
    }

    private fun initOptions() {
        val list = arrayListOf<MenuModel>()
        list.add(MenuModel(getString(R.string.shuffle), R.drawable.ico_shuffle))
        list.add(MenuModel(getString(R.string.favourites), R.drawable.ico_favorite))
        list.add(MenuModel(getString(R.string.playlist), R.drawable.ico_playlist))

        val recycler = findViewById<View>(R.id.opts) as RecyclerView
        recycler.adapter = OptionsAdapter(list, false, onClick)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.setHasFixedSize(true)
        recycler.isNestedScrollingEnabled = false
    }

    private val onClick = object : OnClick {
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
                startActivity(Intent(this@MainActivity, clazz))
            }
        }
    }

    private fun requestRuntimePerms() {
        val permissionStatus = PermissionStatus(this)
        if (!permissionStatus.validatePermissions()) {
            permissionStatus.confirmPermissionMsg()
            return
        }
    }
}
