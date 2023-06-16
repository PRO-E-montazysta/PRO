package com.example.e_montazysta.ui.returnitem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.databinding.ListItemCreateReturnBinding


class ReturnDialogAdapter(val elements: List<ReleaseItem>) : ListAdapter<ReleaseItem, ReturnDialogAdapter.ViewHolder>(DiffCallback()){

    var elements_list: List<ReleaseItem> = elements
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    // Add a variable to store the selected item count
    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCreateReturnBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = elements[position]
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ListItemCreateReturnBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReleaseItem) {
            binding.releaseItem = item
            binding.executePendingBindings()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ReleaseItem>() {
        override fun areItemsTheSame(oldItem: ReleaseItem, newItem: ReleaseItem): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: ReleaseItem, newItem: ReleaseItem): Boolean {
            return oldItem == newItem
        }
    }
}
