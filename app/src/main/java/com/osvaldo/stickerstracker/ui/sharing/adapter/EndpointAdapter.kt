package com.osvaldo.stickerstracker.ui.sharing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.osvaldo.stickerstracker.data.model.Connection
import com.osvaldo.stickerstracker.databinding.EndpointLayoutBinding

class EndpointAdapter(
    private val listener : (endpointName : String,endpointId : String) -> Unit
) : ListAdapter<Connection, EndpointViewHolder>(ConnectionComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EndpointViewHolder {
        val binding = EndpointLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EndpointViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EndpointViewHolder, position: Int) {
        val connection = getItem(position)
        holder.bind(connection)
        holder.item.setOnClickListener { listener(connection.name,connection.endpointId) }
    }
}