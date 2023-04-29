package com.example.e_montazysta.ui.release

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.databinding.ListItemReleaseBinding

class ReleaseListAdapter(val clickListener: ReleaseClickListener) : RecyclerView.Adapter<ReleaseListAdapter.ViewHolder>() {

    var elements = listOf<ReleaseListItem>()
    private var onClickListener: ReleaseClickListener? = null

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

        fun bind(data: ReleaseListItem, clickListener: ReleaseClickListener) {
            binding.releaseId.text = data.id
            binding.toolType.text = data.type
//            binding.clickListener = clickListener
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
class ReleaseClickListener(val clickListener: (releaseId: String) -> Unit) {
    fun onClick(release: ReleaseListItem) = clickListener(release.id)
}