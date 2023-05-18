package com.example.e_montazysta.ui.release

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentReleaseDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReleaseDetailFragment : Fragment() {
    private val releaseDetailViewModel: ReleaseDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args: ReleaseDetailFragmentArgs by navArgs()
        val releaseId = args.releaseId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentReleaseDetailBinding = FragmentReleaseDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.releaseDetailViewModel = releaseDetailViewModel

        releaseDetailViewModel.getReleaseDetail(releaseId)

        releaseDetailViewModel.releasedetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.releaseId.text = it.id.toString()
                binding.releaseTimeValue.text = it.releaseTime
                binding.returnTimeValue.text = it.returnTime
                binding.receivedByIdValue.text = it.receivedById
                binding.releasedById.text = it.releasedById
                binding.toolIdValue.text = it.toolId
                binding.demandAdHocIdValue.text = it.demandAdHocId
                binding.orderStageIdValue.text = it.orderStageId
            }
        })

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}