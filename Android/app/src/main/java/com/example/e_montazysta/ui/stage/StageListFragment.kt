package com.example.e_montazysta.ui.stage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.databinding.FragmentStagesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StageListFragment : Fragment() {
    private val stageListViewModel: StageListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentStagesBinding = FragmentStagesBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.stageListViewModel = stageListViewModel
        val adapter = StageListAdapter( CustomClickListener{
            stageId -> findNavController().navigate(StageListFragmentDirections.actionStageListFragmentToStageDetailFragment(stageId))
        })

        binding.stageList.adapter = adapter
        stageListViewModel.getStage()

        stageListViewModel.stage.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this
        return binding.root

    }

}