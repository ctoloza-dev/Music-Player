package com.music_player.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music_player.R
import com.music_player.adapters.OptionsAdapter
import com.music_player.databinding.ActivityPlayerBinding
import com.music_player.interfaces.OnClick
import com.music_player.models.MenuModel
import com.music_player.utils.logs.Logger

class PlayerActivity : AppCompatActivity() {
    private var binding: ActivityPlayerBinding? = null
    private var sName = "Unknown Song"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        binding!!.songName = sName
        initOptions()
    }

    private fun initOptions() {
        val list = arrayListOf<MenuModel>()
        list.add(MenuModel(getString(R.string.repeat), R.drawable.ico_repeat))
        list.add(MenuModel(getString(R.string.equalizer), R.drawable.ico_equalizer))
        list.add(MenuModel(getString(R.string.timer), R.drawable.ico_timer))
        list.add(MenuModel(getString(R.string.share), R.drawable.ico_share))

        val recycler = findViewById<View>(R.id.opts) as RecyclerView
        recycler.adapter = OptionsAdapter(list, true, onClick)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.setHasFixedSize(true)
        recycler.isNestedScrollingEnabled = false
    }

    private val onClick = object : OnClick {
        override fun onCLick(v: View) {
            Logger.error("Data: ${v.contentDescription}")
        }
    }
}