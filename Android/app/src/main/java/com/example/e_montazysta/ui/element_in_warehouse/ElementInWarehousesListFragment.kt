package com.example.e_montazysta.ui.element_in_warehouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentElementInWarehousesBinding
import com.example.e_montazysta.ui.element.ElementDetailFragmentArgs
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElementInWarehousesListFragment : Fragment() {
    private val elementInWarehousesListViewModel: ElementInWarehousesListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val args: ElementInWarehousesListFragmentArgs by navArgs()
        val elementId = args.elementInWarehouseId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentElementInWarehousesBinding = FragmentElementInWarehousesBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.elementInWarehousesListViewModel = elementInWarehousesListViewModel

        val adapter = ElementInWarehouseListAdapter( CustomClickListener{
                element -> findNavController().navigate(ElementInWarehousesListFragmentDirections.actionElementInWarehousesListFragmentToElementInWarehouseDetailFragment(element.id, element.element))
        })
        binding.warehouseList.adapter = adapter

        elementInWarehousesListViewModel.getElementInWarehouses(elementId)

        elementInWarehousesListViewModel.elements.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        elementInWarehousesListViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
            it?.let {
                if(it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })

        // Wyświetlanie błędów
        elementInWarehousesListViewModel.messageLiveData.observe(viewLifecycleOwner) {
                errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root

    }

}
