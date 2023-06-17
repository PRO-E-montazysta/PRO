package com.example.e_montazysta.ui.order

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.e_montazysta.data.model.Order

class OrderDetailAdapter(fragment: OrderDetailFragment, val order: Order) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OrderDetailDetailsFragment(order)
            1 -> OrderDetailStageListFragment(order)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}

