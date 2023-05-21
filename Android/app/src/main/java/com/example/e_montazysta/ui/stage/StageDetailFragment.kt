package com.example.e_montazysta.ui.stage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentStageDetailBinding
//import com.example.e_montazysta.ui.order.StageDetailFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

class StageDetailFragment : Fragment() {
    private val stageDetailViewModel: StageDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args: StageDetailFragmentArgs by navArgs()
        val stageId = args.stageId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentStageDetailBinding = FragmentStageDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.stageDetailViewModel = stageDetailViewModel

        stageDetailViewModel.getStageDetail(1)

        stageDetailViewModel.stagedetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.nameValue.text = it.name
                binding.statusValue.text = it.id.toString()
                binding.priceValue.text = it.price.toString()
                binding.listOfElementsPlannedNumberValue.text = it.listOfElementsPlannedNumber.toString()
            }
        })

        val create_release = binding.createRelease
        create_release.setOnClickListener {
            val direction = StageDetailFragmentDirections.actionStageDetailFragmentToReleaseCreateFragment(stageId)
        findNavController().navigate(direction)
        }
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}