package com.example.e_montazysta.ui.toollist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_montazysta.databinding.FragmentToolsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ToolsListFragment : Fragment() {
    private val viewModel: ToolsListViewModel by viewModel()
    lateinit var binding: FragmentToolsBinding
    lateinit var adapter: ToolsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentToolsBinding.inflate(inflater, container,false)
        binding.toolsListViewModel = viewModel
        binding.lifecycleOwner = this
        adapter = ToolsListAdapter()
        binding.toolList.adapter = adapter

        viewModel.getTools()

        return binding.root
    }

}