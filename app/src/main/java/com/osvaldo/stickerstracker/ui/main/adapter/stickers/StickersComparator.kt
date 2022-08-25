package com.osvaldo.stickerstracker.ui.main.adapter.stickers

import androidx.recyclerview.widget.DiffUtil
import com.osvaldo.stickerstracker.data.model.Player

object StickersComparator : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.number == newItem.number
    }
}