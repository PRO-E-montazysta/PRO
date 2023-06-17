package com.example.e_montazysta.ui.stage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemPlannedToolBinding


class StageDetailPlannedToolsAdapter(val clickListener: PlannedToolsClickListener) :
    RecyclerView.Adapter<StageDetailPlannedToolsAdapter.ViewHolder>() {

    var elements = listOf<PlannedToolDAO>()
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

    class ViewHolder(val binding: ListItemPlannedToolBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PlannedToolDAO, clickListener: PlannedToolsClickListener) {
            binding.plannedTool = data
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPlannedToolBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PlannedToolsClickListener(val clickListener: (toolId: Int) -> Unit) {

    fun cardClicked(tool: PlannedToolDAO) = clickListener(tool.toolType.id)
}