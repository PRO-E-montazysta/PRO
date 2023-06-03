package com.example.e_montazysta.ui.toollist

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.data.model.Element
import com.example.e_montazysta.data.model.Tool
import com.example.e_montazysta.databinding.FragmentEventDetailBinding
import com.example.e_montazysta.helpers.DateUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.measureTimeMillis

class ToolDetailFragment : Fragment() {
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

        viewModel.eventdetail.observe(viewLifecycleOwner, Observer { event ->
            when(event.item) {
                is Tool -> {
                    binding.itemName.text = "Zgłaszane narzędzie"
                    binding.itemNameValue.text = event.item.name
                    binding.toolbar.subtitle = "${event.item.name} ${event.item.code}"
                }
                is Element -> {
                    binding.itemName.text = "Zgłaszany element"
                    binding.itemNameValue.text = event.item.name
                    binding.toolbar.subtitle = "${event.item.name} ${event.item.code}"
                }
            }
            binding.itemStatusValue.text = event.status.name
            binding.itemStatusValue.setTextColor(event.status.color)
            binding.eventDateValue.text = DateUtil.format(event.eventDate)
            binding.descriptionValue.text = event.description
        })
        return binding.root
    }
}