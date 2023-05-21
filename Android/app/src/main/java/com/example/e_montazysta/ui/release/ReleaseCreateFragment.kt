package com.example.e_montazysta.ui.release

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.R
import com.example.e_montazysta.data.model.mapToReleaseItem
import com.example.e_montazysta.databinding.FragmentCreateReleaseBinding
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReleaseCreateFragment : Fragment() {
    private val releaseCreateViewModel: ReleaseCreateViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: ReleaseCreateFragmentArgs by navArgs()
        val stageId = args.stageId
        val binding: FragmentCreateReleaseBinding = FragmentCreateReleaseBinding.inflate(inflater, container, false)

        binding.viewModel = releaseCreateViewModel



        val adapter = ReleaseCreateAdapter(
            ReleaseCreateAdapter.CustomClickListener {
                item -> val direction = ReleaseCreateFragmentDirections.actionReleaseCreateFragmentToToolFragment(item.toString())
                findNavController().navigate(direction)
            }
        )

        binding.itemList.adapter = adapter

        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE)
            .build()

        val scanner = container?.let { GmsBarcodeScanning.getClient(it.context, options) }
        binding.addObjectsToRelease.setOnClickListener{
            scanner?.startScan()?.addOnSuccessListener {barcode ->
                val code = barcode?.rawValue
                when(code?.first()) {
                    'E' -> binding.viewModel?.let {
                        it.addElementToRelease(code) }
                    'T' -> binding.viewModel?.let {
                        it.addToolToRelease(code) }
                    else -> {
                        Toast.makeText(activity, "Niepoprawny kod QR!\nWartość: $code", Toast.LENGTH_LONG ).show() }
                }
            }?.addOnCanceledListener {

            }?.addOnFailureListener { e ->
                Toast.makeText(activity, "Failed to initialize a scanner.\nError: ${e.message}", Toast.LENGTH_LONG ).show()
            }
        }


        binding.toolbar.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.action_submit -> {
                    binding.viewModel?.createRelease()
                    true
                }
                else -> throw Exception("Błąd dzielenia przez ogórek")
            }
        }


        // Observe the error message LiveData
        releaseCreateViewModel.messageLiveData.observe(viewLifecycleOwner) {
            errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        releaseCreateViewModel.itemsLiveData.observe(viewLifecycleOwner) {
            items -> adapter.elements = items.map {mapToReleaseItem(it)}.toMutableList()
        }

        return binding.root
    }

}