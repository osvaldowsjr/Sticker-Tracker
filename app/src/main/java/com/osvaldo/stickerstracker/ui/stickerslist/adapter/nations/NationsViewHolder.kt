package com.osvaldo.stickerstracker.ui.stickerslist.adapter.nations

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.databinding.NationLayoutBinding
import com.osvaldo.stickerstracker.ui.stickerslist.adapter.stickers.StickersAdapter

class NationsViewHolder(itemView: NationLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
    private val imageFlag = itemView.flagDrawable
    private val nationName = itemView.nationName
    val itemLayout = itemView.itemLayout
    val playersList = itemView.playersList

    fun bind(nation: Nation){
        imageFlag.setImageResource(nation.nationFlag)
        nationName.text = nation.nationName
        createAdapter(nation)
    }

    private fun createAdapter(nation: Nation) {
        val adapter = StickersAdapter()
        adapter.submitList(nation.listOfPlayers)
        playersList.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(this.context, 5)
        }
    }
}