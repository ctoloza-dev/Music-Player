package com.music_player.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.music_player.R
import com.music_player.databinding.BtnItemBinding
import com.music_player.interfaces.OnClick
import com.music_player.models.MenuModel


/**
* Created by David on 28-01-2022.
*/

class OptionsAdapter(
    private var mFiles: ArrayList<MenuModel>,
    private var isImage: Boolean,
    private var onClick: OnClick
) :
    RecyclerView.Adapter<OptionsAdapter.GenViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GenViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.btn_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenViewHolder, position: Int) {
        val item = mFiles[position]
        holder.binding!!.text = item.btn
        holder.binding!!.image = item.drawable
        holder.binding!!.isImage = isImage
        holder.binding!!.onclick = onClick
    }

    override fun getItemCount(): Int = mFiles.size

    inner class GenViewHolder(binding: BtnItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: BtnItemBinding? = null

        init {
            this.binding = binding
        }


    }
}