package com.osvaldo.stickerstracker.ui.sharing.adapter

import androidx.recyclerview.widget.RecyclerView
import com.osvaldo.stickerstracker.data.model.Connection
import com.osvaldo.stickerstracker.databinding.EndpointLayoutBinding

class EndpointViewHolder(itemView : EndpointLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {

    val name = itemView.endpointName
    val item = itemView.itemLayout

    fun bind(connection : Connection){
        name.text = connection.name
    }
}