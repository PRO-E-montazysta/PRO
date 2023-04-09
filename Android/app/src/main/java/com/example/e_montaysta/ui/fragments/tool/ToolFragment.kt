package com.example.e_montaysta.ui.fragments.tool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.e_montaysta.R
import com.example.e_montaysta.data.model.Tool
import com.example.e_montaysta.data.repository.Interfaces.IToolRepository
import com.example.e_montaysta.data.repository.ToolRepository
import com.example.e_montaysta.databinding.FragmentToolBinding
import com.example.e_montaysta.di.ToolsViewModelFactory
import com.example.e_montaysta.ui.viewmodels.ToolViewModel
import com.example.e_montaysta.ui.viewmodels.ToolsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ToolFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ToolFragment : Fragment() {

    private val toolViewModel by viewModel<ToolsViewModel>()

    }
    private lateinit var binding: FragmentToolBinding

    private val observer = Observer<Tool> {
        binding.toolName.text = it.name
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        toolViewModel.tool.observe(viewLifecycleOwner, observer)
        return binding.root
    }
}