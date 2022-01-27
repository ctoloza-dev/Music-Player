package com.music_player

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music_player.adapters.OptionsAdapter
import com.music_player.databinding.ActivityMainBinding
import com.music_player.models.MenuModel
import com.music_player.utils.logs.Logger


class MainActivity : AppCompatActivity() {
    private var totalSongs = 0
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        recycler.adapter = OptionsAdapter(this, list)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.setHasFixedSize(true)
        recycler.isNestedScrollingEnabled = false
        totalSongs = 400
    }


    companion object {
        val onCLick: View.OnClickListener =
            View.OnClickListener { v -> Logger.error("Data: ${v!!.contentDescription} ") }
    }
}
