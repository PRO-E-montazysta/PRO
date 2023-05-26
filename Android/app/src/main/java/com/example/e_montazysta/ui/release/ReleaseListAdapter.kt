package com.example.e_montazysta.ui.release

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemReleaseBinding


class ReleaseListAdapter(val clickListener: CustomClickListener) : RecyclerView.Adapter<ReleaseListAdapter.ViewHolder>(){

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
        holder.bind(item, clickListener)
    }

    class ViewHolder( val binding: ListItemReleaseBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ReleaseListItem, clickListener: CustomClickListener) {
            binding.release = data
            binding.itemClickListener = clickListener
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
class CustomClickListener(val clickListener: (releaseId: Int) -> Unit) {

    fun cardClicked(release: ReleaseListItem) = clickListener(release.id)
}
