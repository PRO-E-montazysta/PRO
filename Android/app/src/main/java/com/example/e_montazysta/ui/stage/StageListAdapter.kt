package com.example.e_montazysta.ui.stage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemStageBinding


class StageListAdapter(val clickListener: CustomClickListener) : RecyclerView.Adapter<StageListAdapter.ViewHolder>(){

    var elements = listOf<StageListItem>()

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

    class ViewHolder( val binding: ListItemStageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StageListItem, clickListener: CustomClickListener) {
            binding.stage = data
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemStageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
class CustomClickListener(val clickListener: (stageId: Int) -> Unit) {

    fun cardClicked(stage: StageListItem) = clickListener(stage.id)
}