package com.osvaldo.stickerstracker.ui.customViews.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.osvaldo.stickerstracker.data.model.NationEnum
import com.osvaldo.stickerstracker.data.model.getFlag
import com.osvaldo.stickerstracker.data.model.getName
import com.osvaldo.stickerstracker.databinding.ItemNationSpinnerBinding

class NationSpinnerAdapter(context: Context, resource: Int, objects: MutableList<NationEnum>) :
    ArrayAdapter<NationEnum>(context, resource, objects) {

    constructor(context: Context, objects: MutableList<NationEnum>) : this(context, 0, objects)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, parent)
    }

    private fun initView(
        position: Int,
        parent: ViewGroup
    ): View {
        val binding =
            ItemNationSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val nationEnum = getItem(position)
        nationEnum?.let {
            binding.ivCountry.setImageResource(it.getFlag())
            binding.tvCountry.text = it.getName()
        }
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