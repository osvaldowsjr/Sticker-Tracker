package com.osvaldo.stickerstracker.ui.stickerslist.adapter.stickers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.databinding.StickerLayoutBinding


class StickersAdapter : ListAdapter<Player, StickersViewHolder>(StickersComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickersViewHolder {
        val binding =
            StickerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StickersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StickersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}