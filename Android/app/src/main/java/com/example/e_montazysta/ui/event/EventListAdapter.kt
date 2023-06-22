package com.example.e_montazysta.ui.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemEventBinding


class EventListAdapter(val clickListener: CustomClickListener) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    var elements = listOf<EventListItem>()
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

    class ViewHolder(val binding: ListItemEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: EventListItem, clickListener: CustomClickListener) {
            binding.event = data
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemEventBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CustomClickListener(val clickListener: (event: EventListItem) -> Unit) {

    fun cardClicked(event: EventListItem) = clickListener(event)
}