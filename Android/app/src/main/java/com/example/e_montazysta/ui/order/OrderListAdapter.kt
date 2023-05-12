package com.example.e_montazysta.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemOrderBinding


class OrderListAdapter(val clickListener: CustomClickListener) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>(){

    var elements = listOf<OrderListItem>()

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

    class ViewHolder( val binding: ListItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: OrderListItem, clickListener: CustomClickListener) {
            binding.order = data
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemOrderBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
class CustomClickListener(val clickListener: (orderId: String) -> Unit) {

    fun cardClicked(order: OrderListItem) = clickListener(order.id)
}