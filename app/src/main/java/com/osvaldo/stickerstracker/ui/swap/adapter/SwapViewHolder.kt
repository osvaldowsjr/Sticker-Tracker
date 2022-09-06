package com.osvaldo.stickerstracker.ui.swap.adapter

import androidx.recyclerview.widget.RecyclerView
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.databinding.SwapLayoutBinding

class SwapViewHolder(itemView : SwapLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {

    private val name = itemView.stickerName
    val layout = itemView.itemLayout

    fun bind(player: Player){
        name.text = player.number
    }
}