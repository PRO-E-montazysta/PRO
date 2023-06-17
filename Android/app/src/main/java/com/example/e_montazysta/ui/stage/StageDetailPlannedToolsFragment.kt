package com.example.e_montazysta.ui.stage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_montazysta.databinding.FragmentStageDetailPlannedToolsBinding

class StageDetailPlannedToolsFragment(val listOfToolsPlannedNumber: List<PlannedToolDAO>) :
    Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentStageDetailPlannedToolsBinding =
            FragmentStageDetailPlannedToolsBinding.inflate(inflater, container, false)

        val adapter = StageDetailPlannedToolsAdapter(PlannedToolsClickListener {
            null
        })
        adapter.elements = listOfToolsPlannedNumber
        binding.list.adapter = adapter

        return binding.root
    }
}
