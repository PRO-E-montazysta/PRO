package com.example.e_montazysta.ui.release

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.databinding.FragmentReleasesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReleaseListFragment : Fragment() {
    private val releaseListViewModel: ReleaseListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentReleasesBinding = FragmentReleasesBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // binding.releaseListViewModel = releaseListViewModel
        val adapter = ReleaseListAdapter(CustomClickListener{
            releaseId -> Toast.makeText(context, releaseId, Toast.LENGTH_LONG).show()
        })

        binding.toolList.adapter = adapter
        releaseListViewModel.getRelease()

        releaseListViewModel.release.observe(viewLifecycleOwner, Observer {
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