package com.music_player.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.music_player.R
import com.music_player.databinding.MusicItemBinding
import com.music_player.models.SongsData

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
        Glide.with(ctx)
            .load(item.artUri)
            .apply(RequestOptions().placeholder(R.drawable.splash).centerCrop())
            .into(holder.bind.songImage)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}