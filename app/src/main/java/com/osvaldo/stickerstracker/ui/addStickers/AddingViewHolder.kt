package com.osvaldo.stickerstracker.ui.addStickers

import androidx.recyclerview.widget.RecyclerView
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.databinding.AddingItemLayoutBinding

class AddingViewHolder(itemView: AddingItemLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
    val name = itemView.stickerName
    val amount = itemView.stickerAmount
    val buttonPlus = itemView.buttonPlus
    val buttonMinus = itemView.buttonMinus

    fun bind(item: Player) {
        name.text = item.number
        amount.text = item.amount.toString()

        buttonPlus.setOnClickListener {
            item.amount++
            amount.text = item.amount.toString()
        }
        buttonMinus.setOnClickListener {
            if (item.amount > 0) {
                item.amount--
            }
            amount.text = item.amount.toString()
        }
    }
}