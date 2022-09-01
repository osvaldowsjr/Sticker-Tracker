package com.osvaldo.stickerstracker.ui.information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.databinding.MostObtainedLayoutBinding
import com.osvaldo.stickerstracker.ui.stickerslist.adapter.nations.NationsComparator

class MostObtainedAdapter : ListAdapter<Nation, MostObtainedViewHolder>(
    NationsComparator
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostObtainedViewHolder {
        val binding =
            MostObtainedLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MostObtainedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MostObtainedViewHolder, position: Int) {
        val nation = getItem(position)
        holder.bind(nation)
    }
}