package com.example.e_montazysta.ui.warehouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.databinding.FragmentWarehousesBinding
//import com.example.e_montazysta.ui.activities.ElementMainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class WarehouseListFragment : Fragment() {
    private val viewModel: WarehouseListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentWarehousesBinding = FragmentWarehousesBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it. --NOT IMPLEMENTED--

        val adapter = WarehouseListAdapter( CustomClickListener{
                warehouseId -> findNavController().navigate(WarehouseListFragmentDirections.actionWarehouseListFragmentToWarehouseDetailFragment(warehouseId))
        })
        binding.warehouseList.adapter = adapter

        viewModel.getWarehouses()

        viewModel.warehouse.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.warehouses = it
            }
        })

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
            it?.let {
                if(it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })

        // Wyświetlanie błędów
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
                errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this
        return binding.root

    }

}
