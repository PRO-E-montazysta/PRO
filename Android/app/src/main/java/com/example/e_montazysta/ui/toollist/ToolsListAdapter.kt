package com.example.e_montazysta.ui.toollist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.databinding.ListItemToolBinding
import com.example.e_montazysta.ui.release.ReleaseCreateAdapter

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
        holder.bind(item)
    }
    class ViewHolder( val binding: ListItemToolBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ToolListItem) {
            binding.toolName.text = data.name
            binding.toolCode.text = data.code
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