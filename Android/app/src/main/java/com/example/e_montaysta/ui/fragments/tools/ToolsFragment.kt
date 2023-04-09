package com.example.e_montaysta.ui.fragments.tools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.e_montaysta.ui.adapters.ToolsAdapter
import com.example.e_montaysta.databinding.FragmentToolsBinding
import com.example.e_montaysta.ui.viewmodels.ToolsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ToolsFragment : Fragment() {
    private val viewModel: ToolsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentToolsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // Set up the RecyclerView
        val adapter = ToolsAdapter()
        binding.toolsRecyclerView.adapter = adapter

        // Observe the list of tools from the ViewModel
        viewModel.tools.observe(viewLifecycleOwner) { tools ->
            adapter.submitList(tools)
        }

        return binding.root
    }
}