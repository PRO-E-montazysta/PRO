package com.example.e_montazysta.ui.release

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemReleaseBinding
import com.example.e_montazysta.databinding.ListItemToolBinding

class ReleaseListAdapter : RecyclerView.Adapter<ReleaseListAdapter.ViewHolder>() {

    var elements = listOf<ReleaseListItem>()

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
        holder.bind(item)
    }
    class ViewHolder( val binding: ListItemReleaseBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ReleaseListItem) {
            binding.releaseId.text = data.id
            binding.toolType.text = data.type
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemReleaseBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}