package com.example.e_montazysta.ui.toollist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.R
import com.example.e_montazysta.databinding.FragmentToolsBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.koin.androidx.viewmodel.ext.android.viewModel

class ToolsListFragment : Fragment() {
    private val toolsListViewModel: ToolsListViewModel by viewModel()
    private lateinit var binding: FragmentToolsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        binding = FragmentToolsBinding.inflate(inflater, container, false)

        val nameFilter = binding.rightDrawer.nameFilterEditText
        val codeFilter = binding.rightDrawer.codeFilterEditText
        val warehouseFilter = binding.rightDrawer.warehouseChipGroup
        val typeFilter = binding.rightDrawer.typeChipGroup

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.toolsListViewModel = toolsListViewModel

        val adapter = ToolsListAdapter(ToolsListAdapter.CustomClickListener {
                toolId -> findNavController().navigate(ToolsListFragmentDirections.actionToolsFragmentToToolDetailFragment(toolId))
        })

        binding.list.adapter = adapter

        toolsListViewModel.getFilterTools()

        toolsListViewModel.tools.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        toolsListViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
            it?.let {
                if(it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })

        // Szuflada
        val drawerLayout: DrawerLayout = binding.drawerLayout

        toolsListViewModel.getListOfWarehouse()
        toolsListViewModel.getListOfToolType()


        // Toolbar
        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter -> {
                    drawerLayout.openDrawer(GravityCompat.END)
                    true
                } else -> {
                false}
            }
        }
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }


        // Wyświetlanie błędów
        toolsListViewModel.messageLiveData.observe(viewLifecycleOwner) {
            errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        toolsListViewModel.toolTypesLiveData.observe(viewLifecycleOwner) {
            typeFilter.removeAllViews()
            populateTypeChips(typeFilter)
        }

        toolsListViewModel.warehouseLiveData.observe(viewLifecycleOwner) {
            warehouseFilter.removeAllViews()
            populateWarehouseChips(warehouseFilter)
        }

        val submitFilterButton = binding.rightDrawer.submitFilterButton

        submitFilterButton.setOnClickListener {

            val name = if (!nameFilter.text.isNullOrBlank()) nameFilter.text.toString() else null
            val code = if (!codeFilter.text.isNullOrBlank()) codeFilter.text.toString() else null
            val warehouses = if (!getSelectedWarehouseChips(warehouseFilter).isNullOrEmpty()) getSelectedWarehouseChips(warehouseFilter) else null
            val types = if (!getSelectedTypeChips(typeFilter).isNullOrEmpty()) getSelectedTypeChips(typeFilter) else null

            toolsListViewModel.filterDataChanged("name", name)
            toolsListViewModel.filterDataChanged("code", code)
            toolsListViewModel.filterDataChanged("warehouse_Id", warehouses?.joinToString(","))
            toolsListViewModel.filterDataChanged("toolType_Id", types?.joinToString(","))
            toolsListViewModel.getFilterTools()
            drawerLayout.closeDrawer(GravityCompat.END)
        }

        val clearFilterButton = binding.rightDrawer.clearFilterButton

        clearFilterButton.setOnClickListener{
            codeFilter.text?.clear()
            typeFilter.clearCheck()
            nameFilter.text?.clear()
            warehouseFilter.clearCheck()
            toolsListViewModel.filterClear()
            toolsListViewModel.getFilterTools()
            drawerLayout.closeDrawer(GravityCompat.END)
        }

        binding.lifecycleOwner = this
        return binding.root

    }

    private fun populateTypeChips(chipGroup: ChipGroup) {
        val types = toolsListViewModel.toolTypesLiveData.value
        if (types != null) {
            for (type in types) {
                val chip = Chip(requireContext())
                chip.text = type.name
                chip.isCheckable = true
                chipGroup.addView(chip)
            }
        }
    }

    private fun populateWarehouseChips(chipGroup: ChipGroup) {
        val warehouses = toolsListViewModel.warehouseLiveData.value
        if (warehouses != null) {
            for (warehouse in warehouses) {
                val chip = Chip(requireContext())
                chip.text = warehouse.name
                chip.isCheckable = true
                chipGroup.addView(chip)
            }
        }
    }

    private fun getSelectedTypeChips(chipGroup: ChipGroup): List<Int?>? {
        val selectedStatuses: MutableList<String> = ArrayList()
        var matchingTypeIds: List<Int?>? = listOf()
        chipGroup.forEach {view ->
            val chip = view as Chip
            if (chip.isChecked) { selectedStatuses.add(chip.text.toString()) }
            matchingTypeIds = toolsListViewModel.toolTypesLiveData.value
                ?.filter { it.name in selectedStatuses }
                ?.map { it.id }
        }
        return matchingTypeIds
    }

    private fun getSelectedWarehouseChips(chipGroup: ChipGroup): List<Int?>? {
        val selectedWarehouses: MutableList<String> = ArrayList()
        var matchingWarehouseIds: List<Int?>? = listOf()

        chipGroup.forEach {view ->
            val chip = view as Chip
            if (chip.isChecked) { selectedWarehouses.add(chip.text.toString()) }
            matchingWarehouseIds = toolsListViewModel.warehouseLiveData.value
                ?.filter { it.name in selectedWarehouses }
                ?.map { it.id }
        }
        return matchingWarehouseIds
    }
}
