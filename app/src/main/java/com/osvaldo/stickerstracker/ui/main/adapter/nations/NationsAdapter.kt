package com.osvaldo.stickerstracker.ui.main.adapter.nations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.model.Nation

class NationsAdapter : ListAdapter<Nation, NationsViewHolder>(NationsComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nation_layout,parent,false)
        return NationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NationsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}