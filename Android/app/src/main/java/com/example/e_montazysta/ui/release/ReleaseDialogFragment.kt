package com.example.e_montazysta.ui.release

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.databinding.FragmentCreateReleaseDialogBinding
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReleaseDialogFragment(
    val items: List<ReleaseItem>,
    val stage: Stage,
    val warehouse: WarehouseFilterDAO?
) : DialogFragment() {
    val viewModel: ReleaseCreateViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.example.e_montazysta.R.style.FullScreenDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCreateReleaseDialogBinding = FragmentCreateReleaseDialogBinding.inflate(inflater, container, false)
        val adapter = ReleaseDialogAdapter()
        viewModel.setWarehouse(warehouse)
        binding.list.adapter = adapter
        adapter.setItems(items)
        binding.orderNameValue.text = stage.orderName
        binding.nameValue.text = stage.name
        binding.statusValue.text = stage.status.value
        if (warehouse != null) {
            binding.warehouseNameValue.text = warehouse.name
        } else {
            binding.warehouseNameValue.text = adapter.elements.first().warehouse
        }
        binding.listOfElementsPlannedNumberValue.text = ""


        binding.toolbar.setNavigationOnClickListener{
            dismiss()
            }

        binding.btnConfirm.setOnClickListener(View.OnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val result = async { viewModel.createRelease(items, stage.id) }.await()
                when (result) {
                    is Result.Success -> {
                        findNavController().navigateUp()
                    }
                    is Result.Error -> {
                        dismiss()
                    }
                }
            }
        })

        // Wyświetlanie błędów
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
                errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }
}
