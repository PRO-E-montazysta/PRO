package com.example.e_montazysta.ui.release

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.databinding.FragmentReleasesBinding
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReleaseListFragment(val stage: Stage? = null) : Fragment() {
    private val releaseListViewModel: ReleaseListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentReleasesBinding =
            FragmentReleasesBinding.inflate(inflater, container, false)

        val adapter = ReleaseListAdapter(CustomClickListener { releaseId ->
            val action =
                ReleaseListFragmentDirections.actionReleaseListFragmentToReleaseDetailFragment(
                    releaseId
                )
            findNavController().navigate(action)
        })

        binding.toolList.adapter = adapter

        releaseListViewModel.release.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })
        if (stage == null) {
            releaseListViewModel.getRelease()
        } else {
            releaseListViewModel.setReleaseList(stage)
        }

        releaseListViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            it?.let {
                if (it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })

        // Empty list info
        releaseListViewModel.isEmptyLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            it?.let {
                if (it) {
                    binding.emptyInfo.visibility = View.VISIBLE
                } else {
                    binding.emptyInfo.visibility = View.GONE
                }
            }
        })

        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this
        return binding.root

    }

}
