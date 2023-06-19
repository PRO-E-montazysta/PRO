package com.example.e_montazysta.ui.element_in_warehouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemElementInWarehousesBinding


class ElementInWarehouseListAdapter(val clickListener: CustomClickListener) :
    RecyclerView.Adapter<ElementInWarehouseListAdapter.ViewHolder>() {

    var elements = listOf<ElementInWarehousesDAOItem>()
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

    class ViewHolder(val binding: ListItemElementInWarehousesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ElementInWarehousesDAOItem, clickListener: CustomClickListener) {
            binding.elementInWarehouse = data
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemElementInWarehousesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CustomClickListener(val clickListener: (element: ElementInWarehousesDAOItem) -> Unit) {

    fun cardClicked(element: ElementInWarehousesDAOItem) = clickListener(element)
}