package com.example.e_montazysta.ui.event

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentEventDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import kotlin.system.measureTimeMillis

class EventDetailFragment : Fragment() {
    private val viewModel: EventDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args: EventDetailFragmentArgs by navArgs()
        val eventId = args.eventId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentEventDetailBinding = FragmentEventDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        val time = measureTimeMillis {
            viewModel.getEventDetail(eventId)
        }
        Log.d(TAG, "Requests took $time ms.")

        viewModel.eventdetail.observe(viewLifecycleOwner, Observer {

        })
        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        return binding.root
    }
}