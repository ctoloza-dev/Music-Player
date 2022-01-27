package com.music_player.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.music_player.MainActivity
import com.music_player.R
import com.music_player.models.MenuModel

class OptionsAdapter(
    private var ctx: Context,
    private var mFiles: ArrayList<MenuModel>
) :
    RecyclerView.Adapter<OptionsAdapter.GenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenViewHolder =
        GenViewHolder(LayoutInflater.from(ctx).inflate(R.layout.btn_item, parent, false))

    override fun onBindViewHolder(holder: GenViewHolder, position: Int) {
        val item = mFiles[position]
        holder.option.contentDescription = item.btn
        holder.option.text = item.btn
        holder.option.setCompoundDrawablesWithIntrinsicBounds(0, item.drawable, 0, 0)
        holder.option.setOnClickListener(MainActivity.onCLick)

    }

    override fun getItemCount(): Int = mFiles.size

    class GenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var option: Button = itemView.findViewById(R.id.option)

    }
}