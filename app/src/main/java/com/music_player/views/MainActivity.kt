package com.music_player.views

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music_player.R
import com.music_player.viewmodel.adapters.MusicAdapter
import com.music_player.viewmodel.adapters.OptionsAdapter
import com.music_player.databinding.ActivityMainBinding
import com.music_player.utils.logs.Logger
import com.music_player.viewmodel.MainViewModel
import com.music_player.viewmodel.SongsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by David on 26-02-2022.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val songsViewModel: SongsViewModel by viewModels()
    private var isViewShowed: Boolean = false
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.isPermissionGive.observe(this) { result ->
            binding.total = 0
            if (result) {
                initViews()
            } else {
                mainViewModel.requestPerms()
            }
        }
        mainViewModel.checkPermissions()

    }

    override fun onResume() {
        super.onResume()
        mainViewModel.requestPerms()
    }

    private fun initViews() {
        initOptions()

        val toolbar = binding.toolbar.root as Toolbar?
        toggle = ActionBarDrawerToggle(
            this,
            binding.root as DrawerLayout?,
            toolbar,
            R.string.open,
            R.string.close
        )
        (binding.root as DrawerLayout).addDrawerListener(toggle)
        isViewShowed = true

        showSongs().start()
    }

    private fun initOptions() {
        val search = binding.toolbar.search as SearchView?
        Logger.error("Search !=null ${(search != null)}")

        search!!.findViewById<AppCompatImageView>(androidx.appcompat.R.id.search_close_btn)
            .setOnClickListener {
                search.setQuery("", false)
                search.clearFocus()
            }
        binding.navView.setNavigationItemSelectedListener { item ->
            Logger.error("Clicked ${item.title}")
            true
        }
        val recycler = findViewById<View>(R.id.opts) as RecyclerView
        mainViewModel.optionsMenu.observe(this) { listMenu ->
            recycler.adapter = OptionsAdapter(listMenu, false, mainViewModel.onClick)
            recycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycler.setHasFixedSize(true)
            recycler.isNestedScrollingEnabled = false
        }

        mainViewModel.getMenu()

    }

    private fun showSongs() = CoroutineScope(Dispatchers.Main).launch {
        binding.songsList.setHasFixedSize(true)
        binding.songsList.setItemViewCacheSize(13)
        binding.songsList.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.songsList.adapter = MusicAdapter(this@MainActivity, songsViewModel.getSongs())
        binding.total = binding.songsList.adapter!!.itemCount
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }
}