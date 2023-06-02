package com.example.e_montazysta.ui.stage

//import com.example.e_montazysta.ui.order.StageDetailFragmentArgs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.databinding.FragmentStageDetailDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StageDetailDetailsFragment(val stage: Stage?) : Fragment() {
    private val stageDetailViewModel: StageDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentStageDetailDetailsBinding = FragmentStageDetailDetailsBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.stageDetailViewModel = stageDetailViewModel

        stageDetailViewModel.setStageDetail(stage!!)

        stageDetailViewModel.stagedetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.nameValue.text = it.name
                binding.statusValue.text = it.id.toString()
                binding.priceValue.text = it.price.toString()
                binding.plannedStartValue.text = it.plannedStart.toString()
                binding.plannedEndValue.text = it.plannedEnd.toString()
                binding.plannedFittersNumberValue.text = it.plannedFittersNumber.toString()
                binding.minimumImagesNumberValue.text = it.minimumImagesNumber.toString()
                binding.listOfToolsPlannedNumberValue.text = it.listOfToolsPlannedNumber.toString()
                binding.listOfElementsPlannedNumberValue.text = it.listOfToolsPlannedNumber.toString()
            }
        })

        return binding.root
    }


}