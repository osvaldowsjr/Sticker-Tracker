package com.osvaldo.stickerstracker.ui.addStickers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.databinding.AddingItemLayoutBinding
import com.osvaldo.stickerstracker.ui.stickerslist.adapter.stickers.StickersComparator

class AddingAdapter : ListAdapter<Player, AddingViewHolder>(
    StickersComparator
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddingViewHolder {
        val binding  = AddingItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}