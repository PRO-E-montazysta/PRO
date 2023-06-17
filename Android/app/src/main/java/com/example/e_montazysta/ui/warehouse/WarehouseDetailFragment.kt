package com.example.e_montazysta.ui.warehouse

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentWarehouseDetailBinding
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.measureTimeMillis

class WarehouseDetailFragment : Fragment() {
    private val viewModel: WarehouseDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val args: WarehouseDetailFragmentArgs by navArgs()
        val warehouseId = args.warehouseId

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentWarehouseDetailBinding =
            FragmentWarehouseDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        val time = measureTimeMillis {
            viewModel.getWarehouseDetail(warehouseId)
        }
        Log.d(ContentValues.TAG, "Requests took $time ms.")

        viewModel.tool.observe(viewLifecycleOwner, Observer { warehouse ->
            binding.warehouseNameValue.text = warehouse.name
            binding.warehouseDescriptionValue.text = warehouse.description
            binding.warehouseAdressValue.text = warehouse.location?.let {
                val apartmentNumber = warehouse.location.apartmentNumber
                val apartmentString = if (apartmentNumber != null) "/$apartmentNumber" else ""

                "${warehouse.location.street} " +
                        "${warehouse.location.propertyNumber}" +
                        "$apartmentString, " +
                        "${warehouse.location.zipCode} " +
                        "${warehouse.location.city}"
            }
            binding.warehouseOpeningHoursValue.text = warehouse.openingHours
        })

        // Wyświetlanie błędów
        viewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        // Wyświetlanie błędów
        viewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
}