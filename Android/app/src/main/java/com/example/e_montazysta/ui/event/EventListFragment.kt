package com.example.e_montazysta.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.databinding.FragmentEventsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventListFragment : Fragment() {
    private val eventListViewModel: EventListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentEventsBinding = FragmentEventsBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.eventListViewModel = eventListViewModel
        val adapter = EventListAdapter( CustomClickListener{
            eventId -> findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(eventId))
        })

        binding.eventList.adapter = adapter
        eventListViewModel.getEvent()

        eventListViewModel.event.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        eventListViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
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
