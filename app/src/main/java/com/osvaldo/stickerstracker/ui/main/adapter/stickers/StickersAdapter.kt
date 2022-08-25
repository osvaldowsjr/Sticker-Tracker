package com.osvaldo.stickerstracker.ui.main.adapter.stickers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.model.Player


class StickersAdapter : ListAdapter<Player, StickersViewHolder>(StickersComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sticker_layout,parent,false)
        return StickersViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}