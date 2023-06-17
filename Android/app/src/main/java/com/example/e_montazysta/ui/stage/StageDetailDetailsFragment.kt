package com.example.e_montazysta.ui.stage

//import com.example.e_montazysta.ui.order.StageDetailFragmentArgs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.databinding.FragmentStageDetailDetailsBinding
import com.example.e_montazysta.helpers.DateUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class StageDetailDetailsFragment(val stage: Stage?) : Fragment() {
    private val stageDetailViewModel: StageDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentStageDetailDetailsBinding =
            FragmentStageDetailDetailsBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.stageDetailViewModel = stageDetailViewModel

        stageDetailViewModel.setStageDetail(stage!!)

        stageDetailViewModel.stagedetail.observe(viewLifecycleOwner, Observer {
            it?.let {stage ->
                binding.nameValue.text = stage.name
                binding.statusValue.text = stage.status.value
                binding.priceValue.text = stage.price.toString() + " PLN"
                binding.plannedStartValue.text =
                    if (stage.plannedStart != null) DateUtil.format(stage.plannedStart) else "Brak"
                binding.plannedEndValue.text =
                    if (stage.plannedEnd != null) DateUtil.format(stage.plannedEnd) else "Brak"
                binding.plannedFittersNumberValue.text = stage.plannedFittersNumber.toString()
                binding.minimumImagesNumberValue.text = stage.minimumImagesNumber.toString()
                binding.orderNameValue.text = stage.orderName
                binding.order.setOnClickListener{
                    findNavController().navigate(
                        StageDetailFragmentDirections.actionStageDetailFragmentToOrderDetailFragment(
                            stage.orderId
                        )
                    )
                }
            }
        })
        stageDetailViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            it?.let {
                if (it) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                } else {
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })
        return binding.root
    }


}
