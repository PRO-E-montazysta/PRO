package com.example.e_montazysta.ui.warehouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemWarehouseBinding


class WarehouseListAdapter(val clickListener: CustomClickListener) :
    RecyclerView.Adapter<WarehouseListAdapter.ViewHolder>() {

    var warehouses = listOf<WarehouseFilterDAO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return warehouses.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = warehouses[position]
        holder.bind(item, clickListener)
    }

    class ViewHolder(val binding: ListItemWarehouseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: WarehouseFilterDAO, clickListener: CustomClickListener) {
            binding.warehouse = data
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWarehouseBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CustomClickListener(val clickListener: (warehouseId: Int) -> Unit) {

    fun cardClicked(warehouse: WarehouseFilterDAO) = clickListener(warehouse.id)
}
