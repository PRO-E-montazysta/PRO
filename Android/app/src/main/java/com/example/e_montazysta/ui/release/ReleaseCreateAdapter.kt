package com.example.e_montazysta.ui.release

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.databinding.ListItemCreateReleaseBinding
import com.google.android.material.R


class ReleaseCreateAdapter(val clickListener: CustomClickListener) :
    ListAdapter<ReleaseItem, ReleaseCreateAdapter.ViewHolder>(DiffCallback()) {


    var elements = mutableListOf<ReleaseItem>()
    var selectedItemCount = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // Add a variable to store the selected item count
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

        holder.binding.root.setOnLongClickListener {
            clickListener.cardLongClicked()
            item.isSelected = !item.isSelected
            elements[position].isSelected = item.isSelected

            if (elements[position].isSelected) {
                holder.binding.root.setBackgroundColor(R.attr.colorSurfaceInverse)
                selectedItemCount++
            } else {
                holder.binding.root.setBackgroundColor(Color.TRANSPARENT)
                selectedItemCount--
            }
            clickListener.onItemSelected(selectedItemCount)
            notifyItemChanged(position)
            true
        }
    }

    inner class ViewHolder(val binding: ListItemCreateReleaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReleaseItem, clickListener: CustomClickListener) {
            binding.releaseItem = item
            binding.clickListener = clickListener
            binding.executePendingBindings()

            binding.quantity.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val quantityString = s?.toString()
                    if (quantityString.isNullOrEmpty()) {
                        // If the weight is empty or null, set it to the minimum value of 1
                        item.quantity = 1
                    } else {
                        val quantity = quantityString.toIntOrNull()
                        if (quantity != null && quantity >= 1) {
                            item.quantity = quantity
                        } else {
                            item.quantity = 1
                        }
                    }
                }
            })
        }
    }

    class CustomClickListener(
        val clickListener: (item: ReleaseItem) -> Unit,
        val longClickListener: () -> Unit,
        val itemSelectedListener: (itemCount: Int) -> Unit
    ) {
        fun cardClicked(item: ReleaseItem) = clickListener(item)
        fun cardLongClicked() = longClickListener()
        fun onItemSelected(count: Int): Unit = itemSelectedListener(count)

    }

    private class DiffCallback : DiffUtil.ItemCallback<ReleaseItem>() {
        override fun areItemsTheSame(oldItem: ReleaseItem, newItem: ReleaseItem): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: ReleaseItem, newItem: ReleaseItem): Boolean {
            return oldItem == newItem
        }
    }
}
