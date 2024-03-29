package com.example.e_montazysta.ui.element

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.R
import com.example.e_montazysta.databinding.FragmentElementsBinding
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ElementsListFragment : Fragment() {
    private val elementsListViewModel: ElementsListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentElementsBinding =
            FragmentElementsBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.elementsListViewModel = elementsListViewModel

        val adapter = ElementListAdapter(CustomClickListener { elementId ->
            findNavController().navigate(
                ElementsListFragmentDirections.actionElementsListFragmentToElementMainActivity(
                    elementId
                )
            )
        })
        binding.elementList.adapter = adapter

        elementsListViewModel.getElements()

        elementsListViewModel.elements.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        elementsListViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            it?.let {
                if (it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })

        // Empty list info
        elementsListViewModel.isEmptyLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            it?.let {
                if (it) {
                    binding.emptyInfo.visibility = View.VISIBLE
                } else {
                    binding.emptyInfo.visibility = View.GONE
                }
            }
        })

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        toolbar.menu.findItem(R.id.filter).isVisible = false

        val mSwipeRefreshLayout = binding.swiperefresh
        mSwipeRefreshLayout.setOnRefreshListener {
            elementsListViewModel.getElements()
            mSwipeRefreshLayout.isRefreshing = false
        }

        return binding.root

    }

}
