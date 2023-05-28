package com.example.e_montazysta.ui.notification

import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_montazysta.databinding.ListItemNotificationBinding


class NotificationListAdapter(val clickListener: CustomClickListener) : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>(){

    var elements = listOf<Notification?>()

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

    class ViewHolder( val binding: ListItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Notification?, clickListener: CustomClickListener) {
            val dateFormat = DateFormat.getDateTimeInstance()
            binding.notification = data
            binding.item.supportText =  "${data?.createdBy} | ${dateFormat.format(data?.createdAt)}"
            binding.itemClickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemNotificationBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
class CustomClickListener(val clickListener: (notification: Notification) -> Unit) {

    fun cardClicked(notification: Notification) = clickListener(notification)
}