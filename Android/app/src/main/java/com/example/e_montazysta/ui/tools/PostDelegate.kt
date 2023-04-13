package com.example.e_montazysta.ui.tools

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.e_montazysta.databinding.ToolItemBinding
import com.example.e_montazysta.ui.list.ListAdapter
import com.example.e_montazysta.ui.list.ListAdapterDelegate
import com.example.e_montazysta.ui.list.ListItem
import com.example.e_montazysta.ui.list.ListViewHolder

class ToolDelegate :
    ListAdapterDelegate<ToolItem>() {
    override fun onCreateViewHolder( parent: ViewGroup, adapter: ListAdapter<ToolItem> ) : ListViewHolder<ToolItem> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ToolItemBinding.inflate(inflater, parent, false)
        return ToolViewHolder(
            binding
        )
    }

    override fun isDelegateForDataType(data: ListItem): Boolean {
        return data is ToolItem
    }
}