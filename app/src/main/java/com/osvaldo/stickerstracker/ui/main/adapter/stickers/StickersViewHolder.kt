package com.osvaldo.stickerstracker.ui.main.adapter.stickers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.osvaldo.stickerstracker.data.model.Player
import kotlinx.android.synthetic.main.sticker_layout.view.*

class StickersViewHolder(stickerView : View) : RecyclerView.ViewHolder(stickerView) {
    val stickerId = stickerView.sticker_id
    val stickerAmount = stickerView.sitcker_amount

    fun bind(player: Player){
        stickerId.text = player.number
        stickerAmount.text = player.amount.toString()
    }
}