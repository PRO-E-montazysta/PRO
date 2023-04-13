package com.example.e_montazysta.ui.tools

import android.view.View
import com.example.e_montazysta.databinding.ToolItemBinding
import com.example.e_montazysta.ui.list.ListViewHolder
import kotlinx.android.extensions.LayoutContainer

class ToolViewHolder(
    private val binding: ToolItemBinding,
) : ListViewHolder<ToolItem>(binding), LayoutContainer {
    override val containerView: View?
        get() = itemView

    override fun bind(data: ToolItem) {
        binding.toolName.text = data.name
        binding.toolCode.text = data.code
    }
}
