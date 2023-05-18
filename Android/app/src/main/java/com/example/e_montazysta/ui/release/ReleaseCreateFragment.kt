package com.example.e_montazysta.ui.release

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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

        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC)
            .build()

        val scanner = container?.let { GmsBarcodeScanning.getClient(it.context, options) }
        binding.addObjectsToRelease.setOnClickListener{
            scanner?.startScan()?.addOnSuccessListener {barcode ->
                val code = barcode?.rawValue
                when(code?.first()) {
                    'E' -> binding.viewModel?.let { Toast.makeText(activity, it.addElementToRelease(code), Toast.LENGTH_LONG).show() }
                    'T' -> binding.viewModel?.let { Toast.makeText(activity, it.addToolToRelease(code), Toast.LENGTH_LONG).show() }
                    else -> {
                        Toast.makeText(activity, "Niepoprawny kod QR!\nWartość: $code", Toast.LENGTH_LONG ).show()
                    }
                }
            }?.addOnCanceledListener {

            }?.addOnFailureListener { e ->
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG ).show()
            }
        }


        return binding.root
    }
}