package com.example.e_montazysta.ui.toollist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ToolItemBinding
import com.example.e_montazysta.ui.tools.ToolItem

class ToolsListAdapter : RecyclerView.Adapter<ToolItemViewHolder>() {

    private var elements = listOf<ToolItem>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ToolItemBinding.inflate(layoutInflater, parent, false)
        return ToolItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onBindViewHolder(holder: ToolItemViewHolder, position: Int) {
        val item = elements[position]
        holder.bind(item)
    }

}