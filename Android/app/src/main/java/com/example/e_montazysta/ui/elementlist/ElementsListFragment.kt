package com.example.e_montazysta.ui.elementlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.e_montazysta.databinding.FragmentElementsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElementsListFragment : Fragment() {
    private val elementsListViewModel: ElementsListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentElementsBinding = FragmentElementsBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.elementsListViewModel = elementsListViewModel

        val adapter = ElementsListAdapter()
        binding.elementList.adapter = adapter

        elementsListViewModel.getTools()

        elementsListViewModel.elements.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.setLifecycleOwner(this)
        return binding.root

    }

}