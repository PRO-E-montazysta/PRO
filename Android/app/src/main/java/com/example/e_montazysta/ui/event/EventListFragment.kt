package com.example.e_montazysta.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.R
import com.example.e_montazysta.data.model.EventStatus
import com.example.e_montazysta.data.model.EventType
import com.example.e_montazysta.databinding.FragmentEventsBinding
import com.example.e_montazysta.helpers.DateUtil
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar


class EventListFragment : Fragment() {
    private val viewModel: EventListViewModel by viewModel()
    private lateinit var binding: FragmentEventsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val nameFilter = binding.rightDrawer.nameFilter
        val statusFilter = binding.rightDrawer.statusChipGroup
        val typeFilter = binding.rightDrawer.typeChipGroup
        val startDateFilter = binding.rightDrawer.startDateTimeTextInputEditText
        val endDateFilter = binding.rightDrawer.endDateTimeTextInputEditText


        // Get a reference to the binding object and inflate the fragment views.
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        val adapter = EventListAdapter( CustomClickListener{
            eventId -> findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(eventId))
        })

        binding.list.adapter = adapter
        viewModel.getEvent(null)

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.elements = it
            }
        })

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
            it?.let {
                if(it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })



        // Szuflada
        val toolbar: MaterialToolbar = binding.toolbar
        val drawerLayout: DrawerLayout = binding.drawerLayout

        // Toolbar
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter -> {
                    drawerLayout.openDrawer(GravityCompat.END)
                    true
                } else -> {
                false}
            }
        }

        populateStatusChips(binding.rightDrawer.statusChipGroup)
        populateTypeChips(binding.rightDrawer.typeChipGroup)

        val startDatePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Wybierz zakres")
                .setSelection(
                    MaterialDatePicker.thisMonthInUtcMilliseconds()
                    )
                .build()
        val startTimePicker =
            MaterialTimePicker.Builder()
                .setTitleText("Wybierz zakres")
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setInputMode(INPUT_MODE_CLOCK)
                .build()

        val endDatePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Wybierz zakres")
                .setSelection(
                    MaterialDatePicker.thisMonthInUtcMilliseconds()
                )
                .build()
        val endTimePicker =
            MaterialTimePicker.Builder()
                .setTitleText("Wybierz zakres")
                .setInputMode(INPUT_MODE_CLOCK)
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

        binding.rightDrawer.startDateTimeTextInputEditText.setOnClickListener {
            startDatePicker.show(childFragmentManager, "tag")
        }

        startDatePicker.addOnPositiveButtonClickListener {
                startTimePicker.show(childFragmentManager, "tag")
        }

        startTimePicker.addOnPositiveButtonClickListener {
            val startDate = Calendar.Builder()
                .setInstant(startDatePicker.selection!!)
                .build()
            startDate.set(Calendar.HOUR_OF_DAY, startTimePicker.hour)
            startDate.set(Calendar.MINUTE, startTimePicker.minute)
            val startDateString: String = DateUtil.format(startDate.time)
            binding.rightDrawer.startDateTimeTextInputEditText.setText(startDateString)
            binding.rightDrawer.startDateTimeTextInputEditText.clearFocus()
        }

        binding.rightDrawer.endDateTimeTextInputEditText.setOnClickListener {
            endDatePicker.show(childFragmentManager, "tag")
        }

        endDatePicker.addOnPositiveButtonClickListener {
            endTimePicker.show(childFragmentManager, "tag")
        }

        endTimePicker.addOnPositiveButtonClickListener {
            val startDate = Calendar.Builder()
                .setInstant(startDatePicker.selection!!)
                .build()
            startDate.set(Calendar.HOUR_OF_DAY, endTimePicker.hour)
            startDate.set(Calendar.MINUTE, endTimePicker.minute)
            val startDateString: String = DateUtil.format(startDate.time)
            binding.rightDrawer.endDateTimeTextInputEditText.setText(startDateString)
            binding.rightDrawer.endDateTimeTextInputEditText.clearFocus()
        }


        val submitFilterButton = binding.rightDrawer.submitFilterButton

        submitFilterButton.setOnClickListener {
            val statuses = getSelectedChips(statusFilter)
            val types = getSelectedChips(typeFilter)
            val name = nameFilter.editText.toString()
            val startDate =
        }

        // Wyświetlanie błędów
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
                errorMessage -> Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    binding.lifecycleOwner = this
    return binding.root
    }

    private fun populateStatusChips(chipGroup: ChipGroup) {
        val statuses = EventStatus.values()
        for (status in statuses) {
            val chip = Chip(requireContext())
            chip.text = status.value
            chip.isCheckable = true
            chipGroup.addView(chip)
        }
    }
    private fun populateTypeChips(chipGroup: ChipGroup) {
        val types = EventType.values()
        for (status in types) {
            val chip = Chip(requireContext())
            chip.text = status.value
            chip.isCheckable = true
            chipGroup.addView(chip)
        }
    }

    private fun getSelectedChips(chipGroup: ChipGroup): List<String>? {
        val selectedStatuses: MutableList<String> = ArrayList()
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedStatuses.add(chip.text.toString())
            }
        }
        return selectedStatuses
    }
}
