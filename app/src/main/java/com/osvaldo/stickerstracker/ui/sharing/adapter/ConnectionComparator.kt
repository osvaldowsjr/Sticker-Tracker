package com.osvaldo.stickerstracker.ui.sharing.adapter

import androidx.recyclerview.widget.DiffUtil
import com.osvaldo.stickerstracker.data.model.Connection

object ConnectionComparator : DiffUtil.ItemCallback<Connection>() {
    override fun areItemsTheSame(oldItem: Connection, newItem: Connection): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Connection, newItem: Connection): Boolean {
        return oldItem.endpointId == newItem.endpointId
    }
}