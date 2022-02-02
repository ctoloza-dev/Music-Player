package com.music_player.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore.Audio.Media
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music_player.R
import com.music_player.adapters.MusicAdapter
import com.music_player.adapters.OptionsAdapter
import com.music_player.databinding.ActivityMainBinding
import com.music_player.interfaces.OnClick
import com.music_player.models.MenuModel
import com.music_player.models.SongsData
import com.music_player.utils.PermissionStatus
import com.music_player.utils.logs.Logger
import java.io.File


class MainActivity : AppCompatActivity() {
    private var totalSongs = 0
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        binding.total = totalSongs
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onResume() {
        super.onResume()
        requestRuntimePerms()
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

        /*val musicList = arrayListOf<String>()
        for (i in 1..400) {
            musicList.add("$i Song")
        }*/
        val musicList = getAllAudio()
        Logger.error(musicList.toString())

        binding.songsList.setHasFixedSize(true)
        binding.songsList.setItemViewCacheSize(13)
        binding.songsList.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        musicAdapter = MusicAdapter(this@MainActivity, musicList)
        binding.songsList.adapter = musicAdapter
        totalSongs = musicAdapter.itemCount

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
        val permissionStatus = PermissionStatus(this@MainActivity)
        if (!permissionStatus.validatePermissions()) {
            permissionStatus.confirmPermissionMsg()
            return
        }
    }

    override fun onBackPressed() {
        //commented to avoid go to splash view
    }

    private fun getAllAudio(): ArrayList<SongsData> {
        val list = arrayListOf<SongsData>()
        val selection = Media.IS_MUSIC + " !=0"
        val projection = arrayOf(
            Media._ID,
            Media.TITLE,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.DATA,
            Media.DATE_ADDED
        )
        val cursor = this.contentResolver.query(
            Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            Media.DATE_ADDED + " DESC",
            null
        )
        val music = HashMap<Int, String>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                projection.forEachIndexed { index, s ->
                    if (s != Media.DATE_ADDED) {
                        music[index] = cursor.getString(cursor.getColumnIndex(s))
                        val song =
                            SongsData(
                                music[0],
                                music[1],
                                music[2],
                                music[3],
                                music[4],
                                music[5]!!.toLong()
                            )
                        val file = File(song.path!!)
                        if (file.exists())
                            list.add(song)

                    }
                }
                Logger.error("Data:\n${music}")
            } while (cursor.moveToNext())
            cursor.close()
        }
        return list
    }
}
