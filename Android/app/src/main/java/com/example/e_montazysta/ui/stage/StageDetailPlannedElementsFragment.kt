package com.example.e_montazysta.ui.stage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.databinding.FragmentStageDetailPlannedElementsBinding

class StageDetailPlannedElementsFragment(val listOfElementsPlannedNumber: List<PlannedElementDAO>) :
    Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentStageDetailPlannedElementsBinding =
            FragmentStageDetailPlannedElementsBinding.inflate(inflater, container, false)

        val adapter = StageDetailPlannedElementsAdapter(PlannedElementClickListener { elementId ->
            findNavController().navigate(
                StageDetailFragmentDirections.actionStageDetailFragmentToElementDetailFragment(
                    elementId
                )
            )
        })
        adapter.elements = listOfElementsPlannedNumber
        binding.list.adapter = adapter

        return binding.root
    }
}
