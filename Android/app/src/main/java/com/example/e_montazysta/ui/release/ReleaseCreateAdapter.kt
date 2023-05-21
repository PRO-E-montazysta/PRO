package com.example.e_montazysta.ui.release

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.databinding.ListItemCreateReleaseBinding


class ReleaseCreateAdapter(val clickListener: CustomClickListener) : RecyclerView.Adapter<ReleaseCreateAdapter.ViewHolder>(){


    var elements = mutableListOf<ReleaseItem>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCreateReleaseBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = elements[position]
        holder.bind(item, clickListener)
    }

    inner class ViewHolder(private val binding: ListItemCreateReleaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReleaseItem, clickListener: CustomClickListener) {
            binding.releaseItem = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    private companion object {
        private const val VIEW_TYPE_ELEMENT = 0
        private const val VIEW_TYPE_TOOL = 1
    }

    class CustomClickListener(val clickListener: (item: Any) -> Unit) {
        fun cardClicked(item: Any) = clickListener(item)
    }
}
