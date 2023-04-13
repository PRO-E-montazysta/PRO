package com.example.e_montazysta.ui.list

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ListViewHolder<T : ListItem>(binding: ViewBinding)
    : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(data: T)
}
