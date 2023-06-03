package com.example.e_montazysta.ui.toollist

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentToolDetailBinding
import com.example.e_montazysta.helpers.DateUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.measureTimeMillis

class ToolDetailFragment : Fragment() {
    private val viewModel: ToolDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args: ToolDetailFragmentArgs by navArgs()
        val eventId = args.toolId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentToolDetailBinding = FragmentToolDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        val time = measureTimeMillis {
            viewModel.getToolDetail(eventId)
        }
        Log.d(TAG, "Requests took $time ms.")

        viewModel.tool.observe(viewLifecycleOwner, Observer { tool ->
            binding.toolNameValue.text = tool.name
            binding.toolCodeValue.text = tool.code
            binding.toolWarehouseValue.text = tool.warehouse.name
            binding.toolCreatedAtValue.text = DateUtil.format(tool.createdAt)
            binding.toolTypeValue.text = tool.toolType.name
        })

        // Wyświetlanie błędów
        viewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
        return binding.root
    }
}