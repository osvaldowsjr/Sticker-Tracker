package com.osvaldo.stickerstracker.ui.swap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.databinding.SwapLayoutBinding
import com.osvaldo.stickerstracker.ui.stickerslist.adapter.stickers.StickersComparator

class SwapAdapter(
    private val listener: (stickerNumber: String) -> Unit
) : ListAdapter<Player, SwapViewHolder>(StickersComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwapViewHolder {
        val binding = SwapLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SwapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SwapViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.layout.setOnClickListener {
            listener(item.number)
        }
    }
}