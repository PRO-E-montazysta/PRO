package com.example.e_montazysta.ui.release

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView

private class ReleaseItemIdKeyProvider(private val recyclerView: RecyclerView) :
    ItemKeyProvider<Int>(SCOPE_MAPPED) {
    override fun getKey(position: Int): Int? {
        val adapter = recyclerView.adapter as? ReleaseCreateAdapter
        return adapter?.getItemId(position)
    }

    override fun getPosition(key: Int): Int {
        val adapter = recyclerView.adapter as? ReleaseCreateAdapter
        return adapter?.getPositionForKey(key) ?: RecyclerView.NO_POSITION
    }
}

private class ReleaseItemLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<String>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<String>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            val holder = recyclerView.getChildViewHolder(view) as? ReleaseCreateAdapter.ViewHolder
            return holder?.getItemDetails()
        }
        return null
    }
}