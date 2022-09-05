package com.osvaldo.stickerstracker.ui.information.adapter

import androidx.recyclerview.widget.RecyclerView
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.model.getFlag
import com.osvaldo.stickerstracker.databinding.MostObtainedLayoutBinding

class MostObtainedViewHolder(itemView: MostObtainedLayoutBinding) :
    RecyclerView.ViewHolder(itemView.root) {

    private val flag = itemView.nationFlag
    private val name = itemView.nationName

    fun bind(nation: Nation) {
        flag.setImageResource(nation.nationEnum.getFlag())
        name.text = nation.nationName
    }
}