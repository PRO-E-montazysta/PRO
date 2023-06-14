package com.example.e_montazysta.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemUserBinding

class UserListAdapter(val clickListener: CustomClickListener): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var elements = listOf<UserListItem>()

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
    class ViewHolder( val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserListItem, clickListener: CustomClickListener) {
            binding.user = data
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemUserBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    class CustomClickListener(val clickListener: (toolId: Int) -> Unit) {
        fun cardClicked(item: UserListItem) = clickListener(item.id)
    }
}
