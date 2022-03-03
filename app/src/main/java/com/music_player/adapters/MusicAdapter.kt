package com.music_player.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.music_player.R
import com.music_player.views.PlayerActivity
import com.music_player.databinding.MusicItemBinding
import com.music_player.models.SongsData
import com.music_player.models.formatDuration

/**
* Created by David on 02-02-2022.
*/

class MusicAdapter(private val ctx: Context, private val musicList: ArrayList<SongsData>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    class ViewHolder(binding: MusicItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MusicItemBinding.inflate(LayoutInflater.from(ctx), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = musicList[position]
        holder.bind.data = item
        holder.bind.duration = formatDuration(item.duration!!)
        Glide.with(ctx)
            .load(item.artUri)
            .apply(RequestOptions().placeholder(R.drawable.splash).centerCrop())
            .into(holder.bind.songImage)
        holder.bind.root.setOnClickListener {
            val intent = Intent(ctx, PlayerActivity::class.java)
            intent.putExtra("index", position)
            intent.putExtra("class", MusicAdapter::class.java)
            ContextCompat.startActivity(ctx, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}