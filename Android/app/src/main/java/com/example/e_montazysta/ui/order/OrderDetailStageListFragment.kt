package com.example.e_montazysta.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.data.model.Order
import com.example.e_montazysta.databinding.FragmentOrderDetailStagesListBinding
import com.example.e_montazysta.ui.stage.CustomClickListener
import com.example.e_montazysta.ui.stage.StageListAdapter
import com.example.e_montazysta.ui.stage.StageListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailStageListFragment(val order: Order? = null) : Fragment() {
    private val stageListViewModel: StageListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentOrderDetailStagesListBinding = FragmentOrderDetailStagesListBinding.inflate(inflater, container, false)
        
        val adapter = StageListAdapter( CustomClickListener{
                stageId -> findNavController().navigate(OrderDetailFragmentDirections.actionOrderDetailFragmentToStageDetailFragment(stageId))
        })

        binding.stageList.adapter = adapter

        if (order == null) {
            stageListViewModel.getStages()
        } else {
            stageListViewModel.setStageList(order.orderStages.filterNotNull())
        }

        stageListViewModel.stage.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        stageListViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
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
