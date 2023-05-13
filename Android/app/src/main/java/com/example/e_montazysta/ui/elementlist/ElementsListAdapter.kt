package com.example.e_montazysta.ui.elementlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemElementBinding

class ElementsListAdapter : RecyclerView.Adapter<ElementsListAdapter.ViewHolder>() {

    var elements = listOf<ElementListItem>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = elements[position]
        holder.bind(item)
    }
    class ViewHolder( val binding: ListItemElementBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ElementListItem) {
            binding.elementName.text = data.name
            binding.elementCode.text = data.code
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemElementBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}