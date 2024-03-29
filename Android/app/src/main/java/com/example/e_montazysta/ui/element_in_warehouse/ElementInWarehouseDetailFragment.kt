package com.example.e_montazysta.ui.element_in_warehouse

//import com.example.e_montazysta.ui.order.ElementDetailFragmentArgs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.e_montazysta.databinding.FragmentElementInWarehouseDetailBinding
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class ElementInWarehouseDetailFragment : Fragment() {

    private val elementInWarehouseDetailViewModel: ElementInWarehouseDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: ElementInWarehouseDetailFragmentArgs by navArgs()
        val elementId = args.elementInWarehouseId
        val elementName = args.elementName

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentElementInWarehouseDetailBinding =
            FragmentElementInWarehouseDetailBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.elementInWarehouseDetailViewModel = elementInWarehouseDetailViewModel

        elementInWarehouseDetailViewModel.getElementInWarehouseDetail(elementId)

        elementInWarehouseDetailViewModel.elementDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.elementInWarehouseCountValue.text = it.inWarehouseCount.toString()
                binding.elementInWarehouseRackValue.text = it.rack
                binding.elementInWarehouseShelfValue.text = it.shelf
            }
        })

        // Wyświetlanie błędów
        elementInWarehouseDetailViewModel.messageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        toolbar.subtitle = elementName

        return binding.root
    }
}
