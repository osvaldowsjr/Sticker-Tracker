package com.osvaldo.stickerstracker.ui.stickerslist.adapter.nations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.databinding.NationLayoutBinding

class NationsAdapter : ListAdapter<Nation, NationsViewHolder>(NationsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationsViewHolder {
        val binding =
            NationLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NationsViewHolder(binding)
    }

    private val onClickListener : View.OnClickListener = View.OnClickListener { view ->
        val position = view?.tag as Int
        val nation = getItem(position)
        nation.isPlayerListVisible = !nation.isPlayerListVisible
        this.notifyItemChanged(position)
    }

    override fun onBindViewHolder(holder: NationsViewHolder, position: Int) {
        val nation = getItem(position)
        holder.bind(nation)
        holder.itemLayout.tag = position
        holder.itemLayout.setOnClickListener(onClickListener)
        if (nation.isPlayerListVisible)
            holder.playersList.visibility = View.VISIBLE
        else
            holder.playersList.visibility = View.GONE

    }
}