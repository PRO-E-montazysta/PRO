package com.example.e_montazysta.ui.fragments.qrscan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.databinding.FragmentQrscanBinding
import com.example.e_montazysta.ui.viewmodels.QRScanViewModel
import com.google.android.gms.common.api.OptionalModuleApi
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import org.koin.androidx.viewmodel.ext.android.viewModel

class QRScanFragment : Fragment() {

    private var _binding: FragmentQrscanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var scannerOptions: GmsBarcodeScannerOptions
    lateinit var moduleInstallClient: ModuleInstallClient
    lateinit var scanner: GmsBarcodeScanner
    val viewModel: QRScanViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        setupScanner()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentQrscanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textQrscan
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun setupScanner() {
        scannerOptions = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            )
            .build()

        moduleInstallClient = ModuleInstall.getClient(requireContext())
        scanner = GmsBarcodeScanning.getClient(requireContext(), scannerOptions)

        installApiModule(moduleInstallClient, scanner)
        scanner.startScan()?.addOnSuccessListener { barcode ->
            val code = barcode?.rawValue
            when (code?.first()) {
                'E' -> {
//                    viewModel.getElementByCode(code)?.let {
//                        val action =
//                            QRScanFragmentDirections.actionNavigationQrscanToElementDetailFragment(
//                                it.id
//                            )
//                        findNavController().navigate(action)
                    }

                'T' -> {
//                    viewModel.getToolByCode(code)?.let {
//                        val action =
//                            QRScanFragmentDirections.actionNavigationQrscanToToolDetailFragment(
//                                it.id
//                            )
//                        findNavController().navigate(action)
//                    }
                }

                else -> {
                    Toast.makeText(
                        activity,
                        "Niepoprawny kod QR!\nWartość: $code",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }?.addOnCanceledListener {
            findNavController().navigateUp()
        }?.addOnFailureListener { e ->
            Toast.makeText(
                activity,
                "Trwa inicjalizacja skanera, spróbuj ponownie.",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigateUp()
        }
    }

    private fun installApiModule(
        moduleInstallClient: ModuleInstallClient,
        module: OptionalModuleApi
    ) {
        moduleInstallClient
            .areModulesAvailable(module)
            .addOnSuccessListener {
                if (it.areModulesAvailable()) {
                    // Modules are present on the device...
                } else {
                    val moduleInstallRequest =
                        ModuleInstallRequest.newBuilder()
                            .addApi(module)
                            .build()
                    moduleInstallClient
                        .installModules(moduleInstallRequest)
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(activity, exception.message, Toast.LENGTH_LONG).show()
                        }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, exception.message, Toast.LENGTH_LONG).show()
            }
    }
}