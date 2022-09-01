package com.osvaldo.stickerstracker.ui.stickerslist.adapter.stickers

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.databinding.StickerLayoutBinding

class StickersViewHolder(stickerView: StickerLayoutBinding) :
    RecyclerView.ViewHolder(stickerView.root) {
    private val stickerId = stickerView.stickerId
    private val stickerAmount = stickerView.sitckerAmount
    private val layout = stickerView.wholeLayout

    fun bind(player: Player) {
        stickerId.text = player.number
        stickerAmount.text = player.amount.toString()

        when (player.amount) {
            0 -> {
                layout.setBackgroundColor(ContextCompat.getColor(layout.context, R.color.red))
            }
            1 -> {
                layout.setBackgroundColor(ContextCompat.getColor(layout.context, R.color.green))
            }
            else -> {
                layout.setBackgroundColor(ContextCompat.getColor(layout.context, R.color.blue))
            }

        }
    }
}