package com.example.e_montazysta.ui.toollist

import com.example.e_montazysta.databinding.ToolItemBinding
import com.example.e_montazysta.ui.list.ListViewHolder
import com.example.e_montazysta.ui.tools.ToolItem

class ToolItemViewHolder(
    val binding: ToolItemBinding,
) : ListViewHolder<ToolItem>(binding) {

    override fun bind(data: ToolItem) {
        binding.toolName.text = data.name
        binding.toolCode.text = data.code
    }
}
