package com.example.e_montazysta.ui.release

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.R
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.mapToReleaseItem
import com.example.e_montazysta.databinding.FragmentCreateReleaseBinding
import com.google.android.gms.common.api.OptionalModuleApi
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReleaseCreateFragment : Fragment() {
    private val releaseCreateViewModel: ReleaseCreateViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCreateReleaseBinding = FragmentCreateReleaseBinding.inflate(inflater, container, false)

        val args: ReleaseCreateFragmentArgs by navArgs()
        val stageId = args.stageId

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

        val moduleInstallClient = ModuleInstall.getClient(requireContext())
        val scanner = GmsBarcodeScanning.getClient(requireContext(), options)





        binding.addObjectsToRelease.setOnClickListener{
            installApiModule(moduleInstallClient, scanner)
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
                    if (adapter.elements != null && adapter.elements.isNotEmpty()){
                        showConfirmationDialog(adapter.elements, binding, stageId)
                        true
                    } else {
                        Toast.makeText(context, "Dodaj co namniej jeden przedmiot!", Toast.LENGTH_LONG).show()
                        false
                    }
                }
                else -> {
                    Toast.makeText(context, "Błąd dzielenia przez ogórek", Toast.LENGTH_LONG).show()
                    false}

            }
        }


        // Observe the error message LiveData
        releaseCreateViewModel.messageLiveData.observe(viewLifecycleOwner) {
            errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        releaseCreateViewModel.itemsLiveData.observe(viewLifecycleOwner) {
            items -> adapter.elements = items.map { mapToReleaseItem(it) }.toMutableList()
        }

        return binding.root
    }
    private fun showConfirmationDialog(
        items: List<ReleaseItem>,
        binding: FragmentCreateReleaseBinding,
        stageId: Int
    ) {
        val itemNames = items.map { it.name + ", Ilość: " + it.quantity }.toTypedArray()

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Wydać:")
            .setItems(itemNames, null)
            .setPositiveButton("Wydaj") { dialog, _ ->

                GlobalScope.launch(Dispatchers.Main){
                    val result = async { binding.viewModel!!.createRelease(items, stageId) }.await()
                    when (result) {
                        is Result.Success -> {
                            findNavController().navigateUp()
                        }
                        is Result.Error -> {
                            dialog.dismiss()
                        }

                        else -> {
                            Toast.makeText(activity, "Coś poszło nie tak!" , Toast.LENGTH_LONG).show()
                        }
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun installApiModule(moduleInstallClient: ModuleInstallClient, module: OptionalModuleApi) {
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
                            if (it.areModulesAlreadyInstalled()) {
                                // Modules are already installed when the request is sent.
                            }
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