package com.example.e_montazysta.ui.list

import android.view.ViewGroup

interface AdapterDelegate<T : ListItem> {
    fun onCreateViewHolder(parent: ViewGroup, adapter: ListAdapter<T>): ListViewHolder<T>

    fun onBindViewHolder(viewHolder: ListViewHolder<T>, data: T)

    fun isDelegateForDataType(data: ListItem): Boolean
}