package com.example.e_montazysta.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ToolItemBinding
import com.example.e_montazysta.data.model.Tool


class ToolsAdapter( val tools: List<Tool>) : RecyclerView.Adapter<ToolsAdapter.ToolViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ToolItemBinding.inflate(inflater, parent, false)
        return ToolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToolViewHolder, position: Int) {
        val tool = tools[position]
        holder.bind(tool)
    }

    override fun getItemCount(): Int {
        return tools.size
    }

    class ToolViewHolder(private val binding: ToolItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tool: Tool) {
            binding.toolName.text = tool.name
            binding.toolCode.text = tool.code
        }
    }
}

