package com.osvaldo.stickerstracker.ui.main.adapter.nations

import androidx.recyclerview.widget.DiffUtil
import com.osvaldo.stickerstracker.data.model.Nation

object NationsComparator : DiffUtil.ItemCallback<Nation>() {
    override fun areItemsTheSame(oldItem: Nation, newItem: Nation): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Nation, newItem: Nation): Boolean {
        return oldItem.nationEnum == newItem.nationEnum
    }
}