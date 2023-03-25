package com.example.e_montaysta.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montaysta.data.model.Tool
import com.example.e_montaysta.databinding.RecycleviewToolBinding


class ToolAdapter(private val toolList: List<Tool>): RecyclerView.Adapter<ToolAdapter.ToolViewHolder>(){

    inner class ToolViewHolder(val toolBinding: RecycleviewToolBinding)
        :RecyclerView.ViewHolder(toolBinding.root){
            fun bindTool(tool: Tool){
                toolBinding.toolName.text = tool.name
                toolBinding.toolId.text = tool.id.toString()
                toolBinding.toolCategory.text = tool.category
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolViewHolder {
        return ToolViewHolder(RecycleviewToolBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return toolList.size
    }

    override fun onBindViewHolder(holder: ToolViewHolder, position: Int) {
        val tool = toolList[position]
        holder.bindTool(tool)
    }
}