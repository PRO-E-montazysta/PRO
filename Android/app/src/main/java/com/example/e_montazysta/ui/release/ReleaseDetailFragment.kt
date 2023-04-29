package com.example.e_montazysta.ui.release

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.e_montazysta.databinding.FragmentReleaseDetailBinding
import com.example.e_montazysta.databinding.FragmentReleasesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReleaseDetailFragment : Fragment() {
    private val releaseDetailViewModel: ReleaseDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentReleaseDetailBinding = FragmentReleaseDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        val id: String = "1"

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.releaseDetailViewModel = releaseDetailViewModel

        releaseDetailViewModel.getReleaseDetail(id)

        releaseDetailViewModel.releasedetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.releaseTime.text = it.releaseTime
            }
        })
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}