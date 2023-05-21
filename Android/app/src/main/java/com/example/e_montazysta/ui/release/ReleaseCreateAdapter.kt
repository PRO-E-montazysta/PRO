package com.example.e_montazysta.ui.release

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.BR
import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.databinding.ListItemElementBinding
import com.example.e_montazysta.databinding.ListItemToolBinding
import com.example.e_montazysta.ui.element.ElementListItem
import com.example.e_montazysta.ui.toollist.ToolListItem


class ReleaseCreateAdapter(val clickListener: CustomClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    var elements = mutableListOf<Any>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            VIEW_TYPE_ELEMENT -> {
                ListItemElementBinding.inflate(layoutInflater, parent, false)
            }
            VIEW_TYPE_TOOL -> {
                ListItemToolBinding.inflate(layoutInflater, parent, false)
            }
            else -> {
                throw IllegalArgumentException("Invalid view type: $viewType")
            }
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = elements[position]
        val viewHolder = holder as ViewHolder
        viewHolder.bind(item, clickListener)
    }

    override fun getItemViewType(position: Int): Int {
        val item = elements[position]
        return when (item) {
            is ElementListItem -> VIEW_TYPE_ELEMENT
            is ToolListItem -> VIEW_TYPE_TOOL
            else -> throw IllegalArgumentException("Invalid item type at position: $position")
        }
    }

    class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Any, clickListener: CustomClickListener) {
            binding.setVariable(BR.item2, item)
            binding.setVariable(BR.itemClickListener, clickListener)

            binding.executePendingBindings()
        }

    }
    private companion object {
        private const val VIEW_TYPE_ELEMENT = 0
        private const val VIEW_TYPE_TOOL = 1
    }

    class CustomClickListener(val clickListener: (releaseId: Int) -> Unit) {

        fun cardClicked(item: Tool) = clickListener(item.id)
        fun cardClicked(item: Element) = clickListener(item.id)

    }
}
