package com.example.e_montazysta.ui.list


abstract class ListAdapterDelegate<T : ListItem> : AdapterDelegate<T> {
    override fun onBindViewHolder(viewHolder: ListViewHolder<T>, data: T) {
        viewHolder.bind(data)
    }
}
