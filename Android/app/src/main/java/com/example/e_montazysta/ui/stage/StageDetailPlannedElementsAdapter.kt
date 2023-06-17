package com.example.e_montazysta.ui.stage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemPlannedElementBinding


class StageDetailPlannedElementsAdapter(val clickListener: PlannedElementClickListener) :
    RecyclerView.Adapter<StageDetailPlannedElementsAdapter.ViewHolder>() {

    var elements = listOf<PlannedElementDAO>()
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

    class ViewHolder(val binding: ListItemPlannedElementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PlannedElementDAO, clickListener: PlannedElementClickListener) {
            binding.plannedElement = data
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPlannedElementBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PlannedElementClickListener(val clickListener: (elementId: Int) -> Unit) {
    fun cardClicked(element: PlannedElementDAO) = clickListener(element.element.id)
}