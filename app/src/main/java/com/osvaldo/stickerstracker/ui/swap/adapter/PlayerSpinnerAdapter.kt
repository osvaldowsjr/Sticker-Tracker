package com.osvaldo.stickerstracker.ui.swap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.databinding.PlayerLayoutBinding

class PlayerSpinnerAdapter(context: Context, resource: Int, objects: MutableList<Player>) :
    ArrayAdapter<Player>(context, resource, objects) {

    constructor(context: Context, objects: MutableList<Player>) : this(context, 0, objects)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, parent)
    }

    private fun initView(position: Int, parent: ViewGroup): View {
        val binding =
            PlayerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val player = getItem(position)
        binding.playerNumber.text = player?.number

        return binding.root
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        return initView(position, parent)
    }
}