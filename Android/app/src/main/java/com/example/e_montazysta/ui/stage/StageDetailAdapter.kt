package com.example.e_montazysta.ui.stage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.ui.release.StageDetailReleaseListFragment

class StageDetailAdapter(fragment: StageDetailFragment, val stage: Stage) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StageDetailDetailsFragment(stage)
            1 -> StageDetailReleaseListFragment(stage)
            2 -> StageDetailPlannedToolsFragment(stage.listOfToolsPlannedNumber)
            3 -> StageDetailPlannedElementsFragment(stage.listOfElementsPlannedNumber)
            else -> throw  IllegalArgumentException("Invalid position: $position")
        }
    }
}

