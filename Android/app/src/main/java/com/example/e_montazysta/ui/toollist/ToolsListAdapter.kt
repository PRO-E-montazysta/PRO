package com.example.e_montazysta.ui.toollist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemToolBinding

class ToolsListAdapter(val clickListener: CustomClickListener): RecyclerView.Adapter<ToolsListAdapter.ViewHolder>() {

    var elements = listOf<ToolListItem>()

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
        holder.bind(item, clickListener)
    }
    class ViewHolder( val binding: ListItemToolBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ToolListItem, clickListener: CustomClickListener) {
            binding.tool = data
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemToolBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    class CustomClickListener(val clickListener: (toolId: Int) -> Unit) {
        fun cardClicked(item: ToolListItem) = clickListener(item.id)
    }
}
