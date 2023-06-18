package com.example.e_montazysta.ui.returnitem

import WarehouseListAdapter
import android.content.Context
import android.os.Bundle
import android.view.ActionMode
import android.view.ActionMode.Callback
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.R
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.databinding.FragmentCreateReturnBinding
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO
import com.google.android.gms.common.api.OptionalModuleApi
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReturnCreateFragment : Fragment() {
    private val viewModel: ReturnCreateViewModel by viewModel()
    private var isBackPressedFromDialog = false
    var actionMode: ActionMode? = null
    lateinit var actionModeCallback: Callback
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentCreateReturnBinding =
            FragmentCreateReturnBinding.inflate(inflater, container, false)

        val args: ReturnCreateFragmentArgs by navArgs()
        val stage = args.stage

        binding.viewModel = viewModel
        viewModel.getListOfWarehouse()
        viewModel.stage = stage

        binding.toolbar.inflateMenu(R.menu.menu_release)

        val adapter = ReturnCreateAdapter(
            ReturnCreateAdapter.CustomClickListener(
                { item ->
                    if (actionMode != null) {
                        Toast.makeText(requireContext(), item.code, Toast.LENGTH_LONG).show()
                    } else {
                        false
                    }
                },
                {
                    if (actionMode == null) {
                        actionMode = requireActivity().startActionMode(actionModeCallback)
                    }
                },
                { itemCount ->
                    actionMode?.title = "Wybrane elementy: $itemCount"
                } // Update the listener to update the ActionMode title with item count

            )
        )

        binding.itemList.adapter = adapter

        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            )
            .build()

        val moduleInstallClient = ModuleInstall.getClient(requireContext())
        val scanner = GmsBarcodeScanning.getClient(requireContext(), options)

        binding.addObjectsToRelease.setOnClickListener {
            installApiModule(moduleInstallClient, scanner)
            scanner.startScan().addOnSuccessListener { barcode ->
                val code = barcode?.rawValue
                when (code?.first()) {
                    'E' -> binding.viewModel?.let {
                        it.addElementToReturn(code)
                        if (viewModel.selectedWarehouseLiveData.value == null) {
                            showWarehouseFilterDialog(
                                requireContext(),
                                viewModel.warehouseLiveData.value!!
                            )
                        }
                    }

                    'T' -> {
                        binding.viewModel?.let {
                            it.addToolToReturn(code)

                        }
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

            }?.addOnFailureListener { e ->
                Toast.makeText(
                    activity,
                    "Trwa inicjalizacja skanera, spróbuj ponownie.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_submit -> {
                    if (adapter.elements != null && adapter.elements.isNotEmpty()) {
                        showConfirmationDialog(
                            adapter.elements,
                            stage,
                            viewModel.selectedWarehouseLiveData.value
                        )
                        true
                    } else {
                        Toast.makeText(
                            context,
                            "Dodaj co namniej jeden przedmiot!",
                            Toast.LENGTH_LONG
                        ).show()
                        false
                    }
                }

                R.id.change_warehouse -> {
                    showWarehouseFilterDialog(
                        requireContext(),
                        viewModel.warehouseLiveData.value!!
                    )
                    true
                }

                else -> {
                    Toast.makeText(context, "Coś poszło nie tak...", Toast.LENGTH_LONG).show()
                    false
                }

            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!isBackPressedFromDialog && adapter.elements.isNotEmpty()) {
                        showDiscardChangesDialog()
                    } else {
                        findNavController().navigateUp()
                    }
                }
            })

        // Observe the error message LiveData
        viewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        viewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            adapter.elements = items.toMutableList()
            adapter.notifyDataSetChanged()
        }

        // Spinner
        viewModel.selectedWarehouseLiveData.observe(viewLifecycleOwner) { warehouse ->
            warehouse?.let {
                binding.toolbar.subtitle = it.name
                binding.toolbar.menu.findItem(R.id.change_warehouse).isVisible = true
            }
        }

        actionModeCallback = object : Callback {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                mode.menuInflater.inflate(R.menu.contextual_action_menu, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                // Handle menu item clicks
                when (item.itemId) {
                    R.id.action_delete -> {
                        adapter.elements.filter { it.isSelected }.forEach {
                            adapter.elements.remove(it)
                        }
                        viewModel._itemsLiveData.postValue(adapter.elements)
                        actionMode?.finish()
                        return true
                    }

                    else -> return false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                val tempList: List<ReleaseItem> = adapter.elements
                tempList.forEach { it.isSelected = false }
                viewModel._itemsLiveData.postValue(tempList)
                adapter.selectedItemCount = 0
                actionMode = null
            }

        }
        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun showConfirmationDialog(
        items: List<ReleaseItem>,
        stage: Stage,
        warehouse: WarehouseFilterDAO?
    ) {
        val fragmentManager = childFragmentManager
        val dialogFragment = ReturnDialogFragment(items, stage, warehouse)
        dialogFragment.show(fragmentManager, "dialog")
    }


    private fun showWarehouseFilterDialog(
        context: Context,
        warehouses: List<WarehouseFilterDAO>
    ) {
        val adapter = WarehouseListAdapter(context, warehouses)
        var selectedWarehouse: WarehouseFilterDAO? = null
        val alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle("Wybierz magazyn")
            .setSingleChoiceItems(adapter, -1) { _, position ->
                selectedWarehouse = warehouses[position]
            }
            .setPositiveButton("Wybierz") { dialog, _ ->
                viewModel.setWarehouse(selectedWarehouse)
                dialog.dismiss()
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
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
                            Toast.makeText(activity, exception.message, Toast.LENGTH_LONG)
                                .show()
                        }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, exception.message, Toast.LENGTH_LONG).show()
            }
    }

    private fun showDiscardChangesDialog() {
        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        alertDialogBuilder.setTitle("Uwaga!")
        alertDialogBuilder.setMessage("Czy na pewno chcesz odrzucić zmiany?")
        alertDialogBuilder.setPositiveButton("Tak") { _, _ ->
            isBackPressedFromDialog = true
            findNavController().navigateUp()
        }
        alertDialogBuilder.setNegativeButton("Nie", null)
        alertDialogBuilder.setOnDismissListener {
            isBackPressedFromDialog = false
        }
        alertDialogBuilder.create().show()
    }
}
