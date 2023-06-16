package com.example.e_montazysta.ui.returnitem

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.databinding.FragmentCreateReleaseReturnDialogBinding
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReturnDialogFragment(
    val items: List<ReleaseItem>,
    val stage: Stage,
    val warehouse: WarehouseFilterDAO?
) : DialogFragment() {
    val viewModel: ReturnCreateViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: com.example.e_montazysta.databinding.FragmentCreateReleaseReturnDialogBinding =
            FragmentCreateReleaseReturnDialogBinding.inflate(inflater, container, false)
        val adapter = ReturnDialogAdapter(items)
        binding.listElements.adapter = adapter

        binding.nameValue.text = stage.name
        binding.status.text = stage.status.value
        if (warehouse != null) {
            binding.warehouseName.text = warehouse.name
        } else {
            binding.warehouseNameValue.text = adapter.elements.first().warehouse
        }


        binding.toolbar.setNavigationOnClickListener{
            dismiss()
            }

        binding.btnConfirm.setOnClickListener(View.OnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val result = async { viewModel.createReturn(items, stage.id) }.await()
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
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow()?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog
    }
}
