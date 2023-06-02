package com.example.e_montazysta.ui.toollist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.e_montazysta.databinding.FragmentToolsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ToolsListFragment : Fragment() {
    private val toolsListViewModel: ToolsListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentToolsBinding = FragmentToolsBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.toolsListViewModel = toolsListViewModel

        val adapter = ToolsListAdapter(ToolsListAdapter.CustomClickListener {
            toolId -> Toast.makeText(context, toolId, Toast.LENGTH_SHORT).show()
        })
        binding.toolList.adapter = adapter

        toolsListViewModel.getTools()

        toolsListViewModel.tools.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        toolsListViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
            it?.let {
                if(it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this
        return binding.root

    }

}
