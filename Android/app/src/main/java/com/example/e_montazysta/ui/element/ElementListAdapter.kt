package com.example.e_montazysta.ui.element

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemElementBinding


class ElementListAdapter(val clickListener: CustomClickListener) : RecyclerView.Adapter<ElementListAdapter.ViewHolder>(){

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
        holder.bind(item, clickListener)
    }

    class ViewHolder( val binding: ListItemElementBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ElementListItem, clickListener: CustomClickListener) {
            binding.item2 = data
            binding.itemClickListener = clickListener
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
class CustomClickListener(val clickListener: (elementId: Int) -> Unit) {

    fun cardClicked(element: ElementListItem) = clickListener(element.id)
}