package com.osvaldo.stickerstracker.ui.main.adapter.nations

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.ui.main.adapter.stickers.StickersAdapter
import kotlinx.android.synthetic.main.nation_layout.view.*

class NationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageFlag = itemView.flag_drawable
    val nationName = itemView.nation_name
    val itemLayout = itemView.item_layout
    val playersList = itemView.players_list

    fun bind(nation: Nation){
        imageFlag.setImageResource(nation.nationFlag)
        nationName.text = nation.nationName
        itemLayout.setOnClickListener {
            if (playersList.visibility == View.VISIBLE) playersList.visibility = View.GONE
            else playersList.visibility = View.VISIBLE
        }
        val adapter = StickersAdapter()
        adapter.submitList(nation.listOfPlayers)
        playersList.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(this.context,5)
        }
    }
}